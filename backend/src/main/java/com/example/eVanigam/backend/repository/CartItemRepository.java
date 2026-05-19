package com.example.eVanigam.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    List<CartItem> findByProduct(Product product);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
    
}
