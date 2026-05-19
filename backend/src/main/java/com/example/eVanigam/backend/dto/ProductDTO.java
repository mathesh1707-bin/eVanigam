package com.example.eVanigam.backend.dto;

public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int stock;
    private String category;

    public ProductDTO(Long productId, String name, String description,
                      double price, String imageUrl, int stock, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.category = category;
    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
}