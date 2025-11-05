package com.ncu.library.borrowservice.dto;

public class BorrowDto {
    private int borrowId;
    private String borrowDate;
    private int userId;
    private UserDto user;  // extra user info

    public BorrowDto() {}

    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int borrowId) { this.borrowId = borrowId; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}



    
    

