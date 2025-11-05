package com.ncu.library.borrowservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ncu.library.borrowservice.dto.BorrowDto;
import com.ncu.library.borrowservice.dto.BorrowResponseDto;
import com.ncu.library.borrowservice.exception.*;
import com.ncu.library.borrowservice.service.BorrowService;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // GET: All borrows
    @GetMapping("/all")
    public ResponseEntity<List<BorrowDto>> getAllBorrows() {
        List<BorrowDto> data = borrowService.getAllBorrows();
        return ResponseEntity.ok(data);
    }

    // GET: Paginated borrows
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getBorrowsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Map<String, Object> data = borrowService.getPaginatedResponse(page, size);
        return ResponseEntity.ok(data);
    }

    // GET: Borrow count
    @GetMapping("/count")
    public ResponseEntity<Integer> getBorrowCount() {
        Integer count = borrowService.getBorrowCount();
        return ResponseEntity.ok(count);
    }

    // GET: Borrow by ID
    @GetMapping("/{borrowId}")
    public ResponseEntity<?> getBorrowById(@PathVariable Integer borrowId) {
        try {
            BorrowDto data = borrowService.getBorrowById(borrowId);
            return ResponseEntity.ok(data);
        } catch (BorrowNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BorrowResponseDto("Borrow not found: " + ex.getMessage()));
        }
    }

    // GET: Borrow by Date
    @GetMapping("/bydate/{borrowDate}")
    public ResponseEntity<?> getBorrowByDate(@PathVariable String borrowDate) {
        try {
            BorrowDto data = borrowService.getBorrowByDate(borrowDate);
            return ResponseEntity.ok(data);
        } catch (BorrowNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BorrowResponseDto("Borrow not found: " + ex.getMessage()));
        }
    }

    // POST: Add Borrow
    @PostMapping("/add")
    public ResponseEntity<BorrowResponseDto> addBorrow(@RequestBody BorrowDto borrowDto) {
        try {
            BorrowResponseDto response = borrowService.addBorrow(borrowDto);
            return ResponseEntity.ok(response);
        } catch (InvalidUserIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BorrowResponseDto("Invalid user ID: " + ex.getMessage()));
        } catch (DuplicateBorrowRecordException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BorrowResponseDto("Duplicate borrow record: " + ex.getMessage()));
        } catch (DatabaseNotAvailableException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new BorrowResponseDto("Database not available: " + ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BorrowResponseDto("Unexpected error: " + ex.getMessage()));
        }
    }
}
