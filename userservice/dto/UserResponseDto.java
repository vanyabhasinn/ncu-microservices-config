package com.ncu.library.userservice.dto;

public class UserResponseDto {

    private Integer userId;
    private String userName;
    private String userLocation;
    private String status;

    // Default Constructor
    public UserResponseDto() {}

    // Constructor with status only
    public UserResponseDto(String status) {
        this.status = status;
    }

    // Constructor with all fields
    public UserResponseDto(Integer userId, String userName, String userLocation, String status) {
        this.userId = userId;
        this.userName = userName;
        this.userLocation = userLocation;
        this.status = status;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
