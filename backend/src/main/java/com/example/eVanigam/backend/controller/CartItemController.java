package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.service.CartItemService;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    
    private CartItemService service;

    public CartItemController(CartItemService service){
        this.service=service;
    }

    @PostMapping("/add/{productId}")
    public CartItem addCartItem(@PathVariable Long productId,@RequestParam int quantity) {
        return service.addItem(productId, quantity);
    }

    @GetMapping
    public List<CartItem> getCartItems() {
        return service.getItems();
    }

    @GetMapping("/{cartItemId}")
    public CartItem getCartItemById(@PathVariable Long cartItemId) {
        return service.getItemById(cartItemId);
    }

    
    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        service.deleteItem(cartItemId);
    }
}
