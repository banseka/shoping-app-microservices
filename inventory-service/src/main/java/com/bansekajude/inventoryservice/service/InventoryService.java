package com.bansekajude.inventoryservice.service;
import com.bansekajude.inventoryservice.repository.InventoryRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepositiry inventoryRepositiry;

    @Transactional(readOnly = true)
    public boolean  isInStock(String skuCode){

        return inventoryRepositiry.findBySkuCode(skuCode).isPresent();

    }
}
