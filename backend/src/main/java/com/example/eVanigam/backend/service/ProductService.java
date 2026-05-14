package com.example.eVanigam.backend.service;

import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.repository.ProductRepository;

@Service
public class ProductService {
    
    private ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }
}
