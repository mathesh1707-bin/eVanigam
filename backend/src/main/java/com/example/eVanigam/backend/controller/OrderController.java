package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.dto.DtoMapper;
import com.example.eVanigam.backend.dto.OrderDTO;
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
    public OrderDTO placeOrder() {
        return DtoMapper.toOrderDTO(service.placeOrder());
    }

    @GetMapping
    public List<OrderDTO> getMyOrders() {
        return service.getMyOrders()
            .stream()
            .map(DtoMapper::toOrderDTO)
            .toList();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return DtoMapper.toOrderDTO(service.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/status")
    public OrderDTO updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        return DtoMapper.toOrderDTO(service.updateStatus(orderId, status));
    }
}