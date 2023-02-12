package com.bansekajude.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.bansekajude.inventoryservice.model.Inventory;
import com.bansekajude.inventoryservice.repository.InventoryRepositiry;
@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepositiry inventoryRepositiry){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Iphone_13");
			inventory.setQuantity(155);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Iphone_12");
			inventory2.setQuantity(12);

			inventoryRepositiry.save(inventory);
			inventoryRepositiry.save(inventory2);
		};
	}

}
