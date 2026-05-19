package com.example.eVanigam.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.exception.ApiException;
import com.example.eVanigam.backend.model.Order;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.OrderRepository;
import com.example.eVanigam.backend.repository.ProductRepository;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public AdminService(UserRepository userRepo, OrderRepository orderRepo, ProductRepository productRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));
        order.setStatus(status);
        return orderRepo.save(order);
    }

    public DashboardStats getDashboardStats() {
        long totalUsers = userRepo.count();
        long totalOrders = orderRepo.count();
        long totalProducts = productRepo.count();
        double totalRevenue = orderRepo.findAll()
                .stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        return new DashboardStats(totalUsers, totalOrders, totalProducts, totalRevenue);
    }

    // Inner class for stats response
    public static class DashboardStats {
        private long totalUsers;
        private long totalOrders;
        private long totalProducts;
        private double totalRevenue;

        public DashboardStats(long totalUsers, long totalOrders, long totalProducts, double totalRevenue) {
            this.totalUsers = totalUsers;
            this.totalOrders = totalOrders;
            this.totalProducts = totalProducts;
            this.totalRevenue = totalRevenue;
        }

        public long getTotalUsers() { return totalUsers; }
        public long getTotalOrders() { return totalOrders; }
        public long getTotalProducts() { return totalProducts; }
        public double getTotalRevenue() { return totalRevenue; }
    }
}