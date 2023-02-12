package com.bansekajude.inventoryservice.service;
import com.bansekajude.inventoryservice.dto.InventoryResponse;
import com.bansekajude.inventoryservice.model.Inventory;
import com.bansekajude.inventoryservice.repository.InventoryRepositiry;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepositiry inventoryRepositiry;

    @Transactional(readOnly = true)
    public List<InventoryResponse>  isInStock(List<String> skuCode){

        log.info("skucodes to search for" + skuCode.toString());
        List<InventoryResponse> response =  inventoryRepositiry.findAllBySkuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
        log.info("all products returned after search" + response.toString());
        return response;
    }
}
