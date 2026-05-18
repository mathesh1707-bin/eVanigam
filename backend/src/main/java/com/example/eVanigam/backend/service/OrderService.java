package com.example.eVanigam.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.OrderEntity;
import com.example.eVanigam.backend.model.OrderItem;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.CartItemRepository;
import com.example.eVanigam.backend.repository.OrderRepository;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class OrderService {

    private OrderRepository orderRepo;
    private CartItemRepository cartRepo;
    private UserRepository userRepo;

    public OrderService(
            OrderRepository orderRepo,
            CartItemRepository cartRepo,
            UserRepository userRepo
    ) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    public OrderEntity placeOrder() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<CartItem> cartItems =
                cartRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;

        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            // Final stock validation
            if (cartItem.getQuantity() > product.getStock()) {
                throw new RuntimeException(
                        product.getName() + " out of stock"
                );
            }

            // Reduce stock
            product.setStock(
                    product.getStock()
                    - cartItem.getQuantity()
            );

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setPriceAtPurchase(
                    product.getprice()
            );

            total +=
                    product.getprice()
                    * cartItem.getQuantity();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        OrderEntity savedOrder =
                orderRepo.save(order);

        // Clear cart after successful order
        cartRepo.deleteAll(cartItems);

        return savedOrder;
    }

    public List<OrderEntity> getMyOrders() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return orderRepo.findByUser(user);
    }
}