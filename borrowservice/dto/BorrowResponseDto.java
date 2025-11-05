package com.ncu.library.borrowservice.dto;

public class BorrowResponseDto {

    private Integer borrowId;
    private String status;

    // 🟢 Default Constructor
    public BorrowResponseDto() {}

    // 🟢 Constructor with parameters
    public BorrowResponseDto(String status) {
        this.status = status;
    }

    public BorrowResponseDto(Integer borrowId, String status) {
        this.borrowId = borrowId;
        this.status = status;
    }

    // 🟢 Getters and Setters
    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
