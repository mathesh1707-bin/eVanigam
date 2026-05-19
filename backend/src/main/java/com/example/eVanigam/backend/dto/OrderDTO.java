package com.example.eVanigam.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private UserDTO user;
    private List<OrderItemDTO> orderItems;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;

    public OrderDTO(Long orderId, UserDTO user, List<OrderItemDTO> orderItems,
                    double totalAmount, String status, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.user = user;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Long getOrderId() { return orderId; }
    public UserDTO getUser() { return user; }
    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
}