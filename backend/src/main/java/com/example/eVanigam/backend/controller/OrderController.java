package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}