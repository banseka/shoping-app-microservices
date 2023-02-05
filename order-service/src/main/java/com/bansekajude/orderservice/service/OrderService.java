package com.bansekajude.orderservice.service;

import com.bansekajude.orderservice.dto.OrderLineItemDto;
import com.bansekajude.orderservice.dto.OrderRequest;
import com.bansekajude.orderservice.model.Order;
import com.bansekajude.orderservice.model.OrderLineItems;
import com.bansekajude.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderRequest orderRequest){
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderItems()
                .stream()
                .map(order -> mapToOrderItem(order))
                .toList();
        newOrder.setOrderLineItems(orderLineItems);

        orderRepository.save(newOrder);
    }

    private OrderLineItems mapToOrderItem(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return  orderLineItem;
    }
}
