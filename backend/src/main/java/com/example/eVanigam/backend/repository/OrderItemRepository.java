package com.example.eVanigam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.OrderItem;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

}