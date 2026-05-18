package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.model.Order;
import com.example.eVanigam.backend.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    public Order placeOrder() {
        return service.placeOrder();
    }

    @GetMapping
    public List<Order> getMyOrders() {
        return service.getMyOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return service.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/status")
    public Order updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        return service.updateStatus(orderId, status);
    }
}