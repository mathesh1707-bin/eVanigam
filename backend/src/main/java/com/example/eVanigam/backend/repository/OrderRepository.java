package com.example.eVanigam.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.OrderEntity;
import com.example.eVanigam.backend.model.User;

public interface OrderRepository
        extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(User user);
}