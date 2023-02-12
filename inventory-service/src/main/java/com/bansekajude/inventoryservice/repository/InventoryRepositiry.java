package com.bansekajude.inventoryservice.repository;

import com.bansekajude.inventoryservice.model.Inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepositiry extends JpaRepository<Inventory, Long> {

    public List<Inventory> findAllBySkuCodeIn(List<String> skuCode);
}
