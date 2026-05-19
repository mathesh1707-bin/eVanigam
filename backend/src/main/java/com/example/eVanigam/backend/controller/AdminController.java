package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.dto.DtoMapper;
import com.example.eVanigam.backend.dto.OrderDTO;
import com.example.eVanigam.backend.dto.UserDTO;
import com.example.eVanigam.backend.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    // All users
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers()
                .stream()
                .map(DtoMapper::toUserDTO)
                .toList();
    }

    // All orders
    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return service.getAllOrders()
                .stream()
                .map(DtoMapper::toOrderDTO)
                .toList();
    }

    // Update order status
    @PutMapping("/orders/{orderId}/status")
    public OrderDTO updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return DtoMapper.toOrderDTO(service.updateOrderStatus(orderId, status));
    }

    // Dashboard stats
    @GetMapping("/stats")
    public AdminService.DashboardStats getStats() {
        return service.getDashboardStats();
    }
}