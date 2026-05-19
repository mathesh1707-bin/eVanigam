package com.example.eVanigam.backend.dto;

import java.util.List;

import com.example.eVanigam.backend.model.CartItem;
import com.example.eVanigam.backend.model.Order;
import com.example.eVanigam.backend.model.OrderItem;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.model.User;

public class DtoMapper {

    public static ProductDTO toProductDTO(Product p) {
        return new ProductDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getImageUrl(),
                p.getStock(),
                p.getCategory()
        );
    }

    public static UserDTO toUserDTO(User u) {
        return new UserDTO(
                u.getUserId(),
                u.getName(),
                u.getEmail(),
                u.getRole()
        );
    }

    public static CartItemDTO toCartItemDTO(CartItem c) {
        return new CartItemDTO(
                c.getCartItemId(),
                toProductDTO(c.getProduct()),
                c.getQuantity()
        );
    }

    public static OrderItemDTO toOrderItemDTO(OrderItem oi) {
        return new OrderItemDTO(
                oi.getId(),
                toProductDTO(oi.getProduct()),
                oi.getQuantity(),
                oi.getPriceAtPurchase()
        );
    }

    public static OrderDTO toOrderDTO(Order o) {
        List<OrderItemDTO> items = o.getOrderItems()
                .stream()
                .map(DtoMapper::toOrderItemDTO)
                .toList();

        return new OrderDTO(
                o.getId(),
                toUserDTO(o.getUser()),
                items,
                o.getTotalAmount(),
                o.getStatus(),
                o.getOrderDate()
        );
    }
}