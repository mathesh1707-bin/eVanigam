package com.example.eVanigam.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    private User user;
    
    @ManyToOne
    private Product product;
    
    private int quantity;

    public CartItem() {
    }

    public CartItem( User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
    
    public User getUser() {
        return user;
    }

    public Long getCartItemId() {
        return cartItemId;
    }


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
