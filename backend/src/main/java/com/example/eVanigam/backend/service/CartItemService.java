package com.example.eVanigam.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.exception.ApiException;
import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.CartItemRepository;
import com.example.eVanigam.backend.repository.ProductRepository;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class CartItemService {

    private CartItemRepository repo;
    private UserRepository userRepo;
    private ProductRepository productRepo;

    public CartItemService(CartItemRepository repo, UserRepository userRepo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public CartItem addItem(Long productId, int quantity) {
        String email = getAuthenticatedEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException("Product not found"));

        if (product.getStock() < quantity) {
            throw new ApiException("Not enough stock available");
        }

        Optional<CartItem> existingCartItem = repo.findByUserAndProduct(user, product);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                throw new ApiException("Not enough stock available");
            }
            cartItem.setQuantity(newQuantity);
            return repo.save(cartItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return repo.save(cartItem);
    }

    public void deleteItem(Long cartItemId) {
        String email = getAuthenticatedEmail();

        CartItem cartItem = repo.findById(cartItemId)
                .orElseThrow(() -> new ApiException("Cart item not found"));

        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new ApiException("Unauthorized delete attempt");
        }

        repo.delete(cartItem);
    }

    public List<CartItem> getItems() {
        String email = getAuthenticatedEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        return repo.findByUser(user);
    }

    public CartItem getItemById(Long cartItemId) {
        String email = getAuthenticatedEmail();

        CartItem cartItem = repo.findById(cartItemId)
                .orElseThrow(() -> new ApiException("Cart item not found"));

        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new ApiException("Unauthorized access");
        }

        return cartItem;
    }

    public CartItem updateQuantity(Long cartItemId, int quantity) {
        String email = getAuthenticatedEmail();

        CartItem cartItem = repo.findById(cartItemId)
                .orElseThrow(() -> new ApiException("Cart item not found"));

        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new ApiException("Unauthorized access");
        }

        if (quantity <= 0) {
            throw new ApiException("Quantity must be greater than 0");
        }

        if (quantity > cartItem.getProduct().getStock()) {
            throw new ApiException("Not enough stock available");
        }

        cartItem.setQuantity(quantity);
        return repo.save(cartItem);
    }

    public double getCartTotal() {
        String email = getAuthenticatedEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        List<CartItem> cartItems = repo.findByUser(user);

        double total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return total;
    }
}