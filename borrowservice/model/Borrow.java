package com.ncu.library.borrowservice.model;

public class Borrow {
    private Integer borrowId;
    private String borrowDate;
    private Integer userId;

    public Borrow() {}

    public Borrow(Integer borrowId, String borrowDate, Integer userId) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.userId = userId;
    }

    public Integer getBorrowId() { return borrowId; }
    public void setBorrowId(Integer borrowId) { this.borrowId = borrowId; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}
