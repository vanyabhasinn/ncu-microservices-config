package com.ncu.library.authservice.dto;

/**
 * Standard response object for Auth Service.
 */
public class AuthResponseDto {

    private boolean success;
    private String message;
    

    public AuthResponseDto() {}

    public AuthResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponseDto(boolean success, String message, String token, Integer userId) {
        this.success = success;
        this.message = message;
        
    }

    // ---------------- Getters and Setters ----------------
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    
}
