package com.bansekajude.inventoryservice.repository;

import com.bansekajude.inventoryservice.model.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepositiry extends JpaRepository<Inventory, Long> {

    public Optional<Inventory> findBySkuCode(String skuCode);
}
