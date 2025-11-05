package com.ncu.library.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ncu.library.authservice.dto.SignupDto;
import com.ncu.library.authservice.dto.AuthDto;
import com.ncu.library.authservice.exception.UserAlreadyExistsException;
import com.ncu.library.authservice.exception.InvalidCredentialsException;
import com.ncu.library.authservice.exception.DBNotFoundException;
import com.ncu.library.authservice.repository.AuthRepository;

@Service
public class AuthService {

    private final AuthRepository _AuthRepository;
    private final PasswordEncoder _PasswordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this._AuthRepository = authRepository;
        this._PasswordEncoder = passwordEncoder;
    }

    // ---------------- SIGNUP ----------------
    public void SignUp(SignupDto cred) {
        if (cred == null || cred.getEmail() == null || cred.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        try {
            // Encode password
            cred.setPassword(_PasswordEncoder.encode(cred.getPassword()));

            // Attempt to signup
            _AuthRepository.SignUp(cred);

        } catch (DBNotFoundException ex) {
            // Could be duplicate or database issue
            throw new UserAlreadyExistsException("User already exists with email: " + cred.getEmail());
        } catch (Exception ex) {
            throw new DBNotFoundException("Database error while signing up: " + ex.getMessage());
        }
    }

    // ---------------- AUTHENTICATE ----------------
    public void Authenticate(AuthDto cred) {
        if (cred == null || cred.getEmail() == null || cred.getPassword() == null) {
            throw new IllegalArgumentException("Email or password cannot be empty");
        }

        try {
            // Get stored password
            String passwordFromDB = _AuthRepository.getPasswordFromEmail(cred.getEmail());

            // Match passwords
            if (!_PasswordEncoder.matches(cred.getPassword(), passwordFromDB)) {
                throw new InvalidCredentialsException("Invalid credentials for email: " + cred.getEmail());
            }

        } catch (DBNotFoundException ex) {
            throw new DBNotFoundException("User not found with email: " + cred.getEmail());
        } catch (InvalidCredentialsException e) {
            throw e; // rethrow for global handler
        } catch (Exception ex) {
            throw new DBNotFoundException("Database error during authentication: " + ex.getMessage());
        }
    }
}
