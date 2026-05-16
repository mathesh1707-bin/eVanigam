package com.example.eVanigam.backend.dto;

import com.example.eVanigam.backend.model.User;

public class LoginResponse {
    private String message;
    private User user;
    private String token;
    
    public LoginResponse() {
    }

    public LoginResponse(String message,User user,String token) {
        this.message = message;
        this.user = user;
        this.token = token;
        

    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
    
    public String getToken() {
        return token;
    }

   

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

}
