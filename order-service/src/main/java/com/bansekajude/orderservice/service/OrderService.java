package com.bansekajude.orderservice.service;

import com.bansekajude.orderservice.dto.InventoryResponse;
import com.bansekajude.orderservice.dto.OrderLineItemDto;
import com.bansekajude.orderservice.dto.OrderRequest;
import com.bansekajude.orderservice.model.Order;
import com.bansekajude.orderservice.model.OrderLineItems;
import com.bansekajude.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;


    public Order createOrder(OrderRequest orderRequest) throws Exception{
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderItems()
                .stream()
                .map(order -> mapToOrderItem(order))
                .toList();
        newOrder.setOrderLineItems(orderLineItems);

        List<String> skuCodes  = orderLineItems.stream().map(item -> item.getSkuCode()).toList();

        InventoryResponse[] response = webClientBuilder.build().get().uri("http://INVENTORY_SERVICE/api/inventory",
        UriBuilder -> UriBuilder.queryParam("skuCode", skuCodes).build())
        .retrieve().bodyToMono(InventoryResponse[].class).block();
        log.info("All products are in stock " + Arrays.stream(response).toList());

        boolean allProductsInStock = Arrays.stream(response).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
        log.info("All products are in stock " + allProductsInStock);

        if (skuCodes.size() != response.length) {
            throw new IllegalArgumentException("product not in stock, please try again later");
        }
        return  orderRepository.save(newOrder);
    }

    private OrderLineItems mapToOrderItem(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return  orderLineItem;
    }
}
