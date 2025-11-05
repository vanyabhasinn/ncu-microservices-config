package com.ncu.library.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ncu.library.authservice.dto.AuthResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------------- User Already Exists ----------------
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AuthResponseDto> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        AuthResponseDto response = new AuthResponseDto();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ---------------- Invalid Credentials ----------------
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<AuthResponseDto> handleInvalidCredentials(InvalidCredentialsException ex) {
        AuthResponseDto response = new AuthResponseDto();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // ---------------- Database Entry Not Found ----------------
    @ExceptionHandler(DBNotFoundException.class)
    public ResponseEntity<AuthResponseDto> handleDBNotFound(DBNotFoundException ex) {
        AuthResponseDto response = new AuthResponseDto();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ---------------- Generic Exception ----------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDto> handleGenericException(Exception ex) {
        AuthResponseDto response = new AuthResponseDto();
        response.setSuccess(false);
        response.setMessage("Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
