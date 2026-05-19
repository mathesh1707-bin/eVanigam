package com.example.eVanigam.backend.exception;

public class ApiException  extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
