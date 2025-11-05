package com.ncu.library.borrowservice.model;

public class User {

    private Integer id;           
    private String userName;      
    private String userLocation;  

    public User() {
        
    }

    public User(Integer id, String userName, String userLocation) {
        this.id = id;
        this.userName = userName;
        this.userLocation = userLocation;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
