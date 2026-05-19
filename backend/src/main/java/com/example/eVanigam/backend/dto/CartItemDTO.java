package com.example.eVanigam.backend.dto;

public class CartItemDTO {
    private Long cartItemId;
    private ProductDTO product;
    private int quantity;
    private double itemTotal;

    public CartItemDTO(Long cartItemId, ProductDTO product, int quantity) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
        this.itemTotal = product.getPrice() * quantity;
    }

    public Long getCartItemId() { return cartItemId; }
    public ProductDTO getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getItemTotal() { return itemTotal; }
}