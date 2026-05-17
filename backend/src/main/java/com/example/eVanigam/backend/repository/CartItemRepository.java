package com.example.eVanigam.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.User;

public interface CartItemRepository extends JpaRepository <CartItem,Long>{
}
