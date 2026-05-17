package com.example.eVanigam.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.repository.CartItemRepository;

@Service
public class CartItemService {
    private CartItemRepository repo;

    public CartItemService(CartItemRepository repo) {
        this.repo = repo;
    }

    //add item
    public CartItem addItem(CartItem cartItem) {
        return repo.save(cartItem);
    }
    
    //delete item
    public void deleteItem(Long id) {
        repo.deleteById(id);
    }

    //get items
    public List<CartItem> getItems() {
        return repo.findAll();
    }

    //get item by id
    public CartItem getItemById(Long id) {
        return repo.findById(id).orElseThrow(()->new RuntimeException("Item not found!"));
    }

}
