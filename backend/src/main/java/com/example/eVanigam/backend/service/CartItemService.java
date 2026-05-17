package com.example.eVanigam.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public CartItemService(CartItemRepository repo,UserRepository userRepo,ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.userRepo=userRepo;
    }

    //add item
    public CartItem addItem(Long productId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // Find user from DB
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find product
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }
        Optional<CartItem> existingCartItem = repo.findByUserAndProduct(user, product);
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                throw new RuntimeException("Not enough stock available");
            }
            cartItem.setQuantity(newQuantity);
            return repo.save(cartItem);
        }
        // Create cart item
        CartItem cartItem = new CartItem();

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        // Save
        return repo.save(cartItem);
    }
    
    //delete item
    public void deleteItem(Long cartItemId) {
        Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
        String email = authentication.getName();
        CartItem cartItem = repo.findById(cartItemId)
            .orElseThrow(() ->new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized delete attempt");
            }
        repo.delete(cartItem);
    }

    //get items
    public List<CartItem> getItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        // Find user from DB
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return repo.findByUser(user);
    }
    
    //get item by id
    public CartItem getItemById(Long cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CartItem cartItem = repo.findById(cartItemId).orElseThrow(() ->new RuntimeException("Cart item not found"));
        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access");
        }
        return cartItem;
    }

    public CartItem updateQuantity(Long cartItemId, int quantity) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        // Find cart item
        CartItem cartItem = repo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Ownership check
        if (!cartItem.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        // Quantity validation
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // Stock validation
        if (quantity > cartItem.getProduct().getStock()) {
            throw new RuntimeException("Not enough stock available");
        }

        // Update quantity
        cartItem.setQuantity(quantity);

        return repo.save(cartItem);
    }
    
    public double getCartTotal() {

    Authentication authentication =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    String email = authentication.getName();

    User user = userRepo.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    List<CartItem> cartItems = repo.findByUser(user);

    double total = 0;

    for (CartItem cartItem : cartItems) {

        double itemTotal =
                cartItem.getProduct().getprice()
                * cartItem.getQuantity();

        total += itemTotal;
    }

    return total;
    }
}
