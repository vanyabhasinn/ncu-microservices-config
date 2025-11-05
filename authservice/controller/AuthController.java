package com.ncu.library.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ncu.library.authservice.dto.SignupDto;
import com.ncu.library.authservice.dto.AuthDto;
import com.ncu.library.authservice.dto.AuthResponseDto;
import com.ncu.library.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService _AuthService;

    @Autowired
    public AuthController(AuthService authService) {
        this._AuthService = authService;
    }

    // ---------------- SIGNUP ----------------
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> SignUp(@RequestBody SignupDto cred) {
        _AuthService.SignUp(cred); // throws UserAlreadyExistsException or DBNotFoundException
        AuthResponseDto response = new AuthResponseDto(true, "User registered successfully");
        return ResponseEntity.ok(response);
    }

    // ---------------- AUTHENTICATE ----------------
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> Authenticate(@RequestBody AuthDto cred) {
        _AuthService.Authenticate(cred); // throws InvalidCredentialsException or DBNotFoundException
        AuthResponseDto response = new AuthResponseDto(true, "Authentication successful");
        return ResponseEntity.ok(response);
    }
}
