package com.bansekajude.productservice.service;

import com.bansekajude.productservice.dto.ProductRequest;
import com.bansekajude.productservice.dto.ProductResponse;
import com.bansekajude.productservice.model.Product;
import com.bansekajude.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.insert(product);
        log.info("Product save with id {}", product.getId());

    }

    public List<ProductResponse> getAllProducts(){
       List<Product> products =  productRepository.findAll();

       return products.stream().map(product -> mapToProductResponse(product)).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
       return ProductResponse.builder()
                .name(product.getName())
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
