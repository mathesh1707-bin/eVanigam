package com.example.eVanigam.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.OrderItem;
import com.example.eVanigam.backend.model.Product;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}