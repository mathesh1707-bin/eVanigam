package com.example.eVanigam.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private double stock;
    private String category;

    public Product() {
    }

    public Product(Long id,String name, String description, double price, String imageUrl, double stock, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.price = price;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public double getStock() {
        return stock;
    }

    public double getprice() {
        return price;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl=imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(double stock) {
        this.stock=stock;
    }

    public void setprice(double price) {
        this.price=price;
    }
    
}
