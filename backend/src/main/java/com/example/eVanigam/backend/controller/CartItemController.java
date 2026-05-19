package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.dto.CartItemDTO;
import com.example.eVanigam.backend.dto.DtoMapper;
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
    public CartItemDTO addCartItem(@PathVariable Long productId, @RequestParam int quantity) {
        return DtoMapper.toCartItemDTO(service.addItem(productId, quantity));
    }

    @GetMapping
    public List<CartItemDTO> getCartItems() {
    return service.getItems()
            .stream()
            .map(DtoMapper::toCartItemDTO)
            .toList();
    }

    @GetMapping("/{cartItemId}")
    public CartItemDTO getCartItemById(@PathVariable Long cartItemId) {
        return DtoMapper.toCartItemDTO(service.getItemById(cartItemId));
    }

    @PostMapping("/update/{cartItemId}")
    public CartItemDTO updateQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        return DtoMapper.toCartItemDTO(service.updateQuantity(cartItemId, quantity));
    }
    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        service.deleteItem(cartItemId);
    }

    @GetMapping("/total")
    public double getCartTotal() {
        return service.getCartTotal();
    }
}
