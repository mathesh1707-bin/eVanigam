package com.example.eVanigam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.Product;

public interface ProductRepository extends JpaRepository <Product,Long> {

    
} 