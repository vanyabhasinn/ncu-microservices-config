package com.ncu.library.borrowservice.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // Injected via @Autowired

import com.ncu.library.borrowservice.dto.BorrowDto;
import com.ncu.library.borrowservice.dto.BorrowResponseDto;
import com.ncu.library.borrowservice.dto.UserDto;
import com.ncu.library.borrowservice.exception.BorrowNotFoundException;
import com.ncu.library.borrowservice.exception.DatabaseNotAvailableException;
import com.ncu.library.borrowservice.exception.DuplicateBorrowRecordException;
import com.ncu.library.borrowservice.exception.InvalidUserIdException;
import com.ncu.library.borrowservice.irepository.iBorrowRepository;
import com.ncu.library.borrowservice.model.Borrow;

@Service
public class BorrowService {

    private final iBorrowRepository borrowRepo;
    private final RestTemplate restTemplate; // CHANGED: injected via Spring

    @Autowired
    public BorrowService(iBorrowRepository borrowRepo, RestTemplate restTemplate) { // CHANGED
        this.borrowRepo = borrowRepo;
        this.restTemplate = restTemplate;  // CHANGED
    }

    // ---------------- ADD BORROW ----------------
    public BorrowResponseDto addBorrow(BorrowDto bDto) {
        UserDto user = fetchUser(bDto.getUserId());
        if (user == null || user.getId() == null || user.getId() == 0) {
            throw new InvalidUserIdException("User not found with ID " + bDto.getUserId());
        }
        try {
            Borrow existing = borrowRepo.getBorrowById(bDto.getBorrowId());
            if (existing != null) {
                throw new DuplicateBorrowRecordException(
                        "Borrow record already exists with ID " + bDto.getBorrowId());
            }
            Borrow borrow = new Borrow();
            borrow.setBorrowId(bDto.getBorrowId());
            borrow.setBorrowDate(bDto.getBorrowDate());
            borrow.setUserId(bDto.getUserId());
            borrowRepo.addBorrow(borrow);
            return new BorrowResponseDto(borrow.getBorrowId(), "Borrow added successfully.");
        } catch (DuplicateBorrowRecordException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseNotAvailableException(
                    "Database not available while adding borrow: " + e.getMessage());
        }
    }

    // ---------------- GET ALL ----------------
    public List<BorrowDto> getAllBorrows() {
        try {
            List<Borrow> borrows = borrowRepo.getAllBorrows();
            List<BorrowDto> dtos = new ArrayList<>();
            for (Borrow b : borrows) {
                BorrowDto dto = new BorrowDto();
                dto.setBorrowId(b.getBorrowId());
                dto.setBorrowDate(b.getBorrowDate());
                dto.setUserId(b.getUserId());
                dto.setUser(fetchUser(b.getUserId()));
                dtos.add(dto);
            }
            return dtos;
        } catch (Exception e) {
            throw new DatabaseNotAvailableException("Error fetching all borrows: " + e.getMessage());
        }
    }

    // ---------------- GET BY PAGE (for pagination) ----------------
    public List<BorrowDto> getBorrowsByPage(int page, int size) {
        try {
            List<Borrow> borrows = borrowRepo.getBorrowsByPage(page, size);
            List<BorrowDto> dtos = new ArrayList<>();
            for (Borrow b : borrows) {
                BorrowDto dto = new BorrowDto();
                dto.setBorrowId(b.getBorrowId());
                dto.setBorrowDate(b.getBorrowDate());
                dto.setUserId(b.getUserId());
                dto.setUser(fetchUser(b.getUserId()));
                dtos.add(dto);
            }
            return dtos;
        } catch (Exception e) {
            throw new DatabaseNotAvailableException("Error fetching paginated borrows: " + e.getMessage());
        }
    }

    // ---------------- PAGINATION RESPONSE ----------------
    public Map<String, Object> getPaginatedResponse(int page, int size) {
        try {
            List<BorrowDto> data = getBorrowsByPage(page, size);
            int totalRecords = getBorrowCount();
            int totalPages = (int) Math.ceil((double) totalRecords / size);
            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("size", size);
            response.put("totalRecords", totalRecords);
            response.put("totalPages", totalPages);
            response.put("data", data);
            return response;
        } catch (Exception e) {
            throw new DatabaseNotAvailableException("Error generating paginated response: " + e.getMessage());
        }
    }

    // ---------------- GET TOTAL COUNT ----------------
    public Integer getBorrowCount() {
        try {
            return borrowRepo.getBorrowCount();
        } catch (Exception e) {
            throw new DatabaseNotAvailableException("Error fetching borrow count: " + e.getMessage());
        }
    }

    // ---------------- GET BY ID ----------------
    public BorrowDto getBorrowById(Integer borrowId) {
        Borrow b = borrowRepo.getBorrowById(borrowId);
        if (b == null) {
            throw new BorrowNotFoundException("No borrow found with ID " + borrowId);
        }
        BorrowDto dto = new BorrowDto();
        dto.setBorrowId(b.getBorrowId());
        dto.setBorrowDate(b.getBorrowDate());
        dto.setUserId(b.getUserId());
        dto.setUser(fetchUser(b.getUserId()));
        return dto;
    }

    // ---------------- GET BY DATE ----------------
    public BorrowDto getBorrowByDate(String borrowDate) {
        Borrow b = borrowRepo.getBorrowByDate(borrowDate);
        if (b == null) {
            throw new BorrowNotFoundException("No borrow found on date " + borrowDate);
        }
        BorrowDto dto = new BorrowDto();
        dto.setBorrowId(b.getBorrowId());
        dto.setBorrowDate(b.getBorrowDate());
        dto.setUserId(b.getUserId());
        dto.setUser(fetchUser(b.getUserId()));
        return dto;
    }

    // ---------------- HELPER: FETCH USER ----------------
    private UserDto fetchUser(int userId) {
        try {
            String url = "http://userservice/users/user/id/" + userId;
            HttpHeaders headers = new HttpHeaders();
            String auth = "userservice:userservice";
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.add("Authorization", "Basic " + encodedAuth);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, UserDto.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("❌ Error fetching user with id " + userId + ": " + e.getMessage());
            return new UserDto();
        }
    }
}
