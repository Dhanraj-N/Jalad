package com.franchiseworld.jalad.model;

public class AuthResponse {

    private String token; // JWT token
    private String message; // Optional message, e.g., "Authentication successful"
    private Long userId;    // Optional: Add more fields if needed (e.g., user ID, email, etc.)
    private String role;    // Optional: Add user role if needed

    // Constructor
    public AuthResponse(String token, String message, Long userId, String role) {
        this.token = token;
        this.message = message;
        this.userId = userId;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
