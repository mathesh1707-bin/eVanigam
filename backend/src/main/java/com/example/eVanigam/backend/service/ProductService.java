package com.example.eVanigam.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.exception.ApiException;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.repository.CartItemRepository;
import com.example.eVanigam.backend.repository.ProductRepository;

@Service
public class ProductService {
    
    private ProductRepository repo;
    private CartItemRepository cartItemRepo;
    public ProductService(ProductRepository repo,CartItemRepository cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
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
    
    public void deleteProduct(Long productId) {
    Product product = repo.findById(productId)
            .orElseThrow(() -> new ApiException("Product not found"));

    // Check if product is in any cart
    if (!cartItemRepo.findByProduct(product).isEmpty()) {
        throw new ApiException("Cannot delete product — it exists in one or more carts");
    }

    repo.deleteById(productId);
}

    public Product updateProduct(Long id, Product product) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setCategory(product.getCategory());
        existing.setDescription(product.getDescription());
        existing.setImageUrl(product.getImageUrl());
        existing.setName(product.getName());
        existing.setStock(product.getStock());
        existing.setPrice(product.getPrice());

        return repo.save(existing);
    }

}