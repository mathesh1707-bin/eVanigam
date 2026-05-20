package com.example.eVanigam.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.Order;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    
}