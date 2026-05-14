package com.example.eVanigam.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.service.ProductService;

@RestController("/product")
public class ProductController {
    private ProductService service;

    public ProductController (ProductService service){
        this.service=service;
    }
    @GetMapping
    public Product addProduct(Product product) {
        return service.addProduct(product);
    }
}
