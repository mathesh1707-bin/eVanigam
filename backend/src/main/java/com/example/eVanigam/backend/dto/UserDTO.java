package com.example.eVanigam.backend.dto;

public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String role;

    public UserDTO(Long userId, String name, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}