package com.example.eVanigam.backend.dto;

public class OrderItemDTO {
    private Long orderItemId;
    private ProductDTO product;
    private int quantity;
    private double priceAtPurchase;
    private double itemTotal;

    public OrderItemDTO(Long orderItemId, ProductDTO product, int quantity, double priceAtPurchase) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.itemTotal = priceAtPurchase * quantity;
    }

    public Long getOrderItemId() { return orderItemId; }
    public ProductDTO getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public double getItemTotal() { return itemTotal; }
}