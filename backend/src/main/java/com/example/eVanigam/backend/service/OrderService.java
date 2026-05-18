package com.example.eVanigam.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.Order;
import com.example.eVanigam.backend.model.OrderItem;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.CartItemRepository;
import com.example.eVanigam.backend.repository.OrderRepository;
import com.example.eVanigam.backend.repository.ProductRepository;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class OrderService {

    private OrderRepository orderRepo;
    private CartItemRepository cartRepo;
    private UserRepository userRepo;
    private ProductRepository productRepo;

    public OrderService(
            OrderRepository orderRepo,
            CartItemRepository cartRepo,
            UserRepository userRepo,
            ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Order placeOrder() {
        User user = getAuthenticatedUser();

        List<CartItem> cartItems = cartRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getStock()) {
                throw new RuntimeException(product.getName() + " does not have enough stock");
            }

            // Reduce and persist stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            total += product.getPrice() * cartItem.getQuantity();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepo.save(order);

        cartRepo.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Order> getMyOrders() {
        User user = getAuthenticatedUser();
        return orderRepo.findByUser(user);
    }

    public Order getOrderById(Long orderId) {
        User user = getAuthenticatedUser();
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getUser().getEmail().equals(user.getEmail())) {
            throw new RuntimeException("Unauthorized access");
        }
        return order;
    }

    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepo.save(order);
    }
}