package com.example.eVanigam.backend.service;

import java.util.List;

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

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setCategory(product.getCategory());
        existing.setDescription(product.getDescription());
        existing.setImageUrl(product.getImageUrl());
        existing.setName(product.getName());
        existing.setStock(product.getStock());
        existing.setprice(product.getprice());

        return repo.save(existing);
    }

}
