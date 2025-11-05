package com.ncu.library.authservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ncu.library.authservice.dto.SignupDto;
import com.ncu.library.authservice.exception.DBNotFoundException;

@Repository("AuthRepository")
public class AuthRepository {

    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public AuthRepository(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    // ---------------- SIGNUP ----------------
    public void SignUp(SignupDto cred) {
        try {
            String query = "INSERT INTO students (s_name, s_email, s_password) VALUES (?, ?, ?)";
            _JdbcTemplate.update(query, cred.getName(), cred.getEmail(), cred.getPassword());
        } catch (Exception ex) {
            throw new DBNotFoundException("Error during student signup: " + ex.getMessage());
        }
    }

    // ---------------- GET PASSWORD BY EMAIL ----------------
    public String getPasswordFromEmail(String email) {
        try {
            String query = "SELECT s_password FROM students WHERE s_email = ?";
            return _JdbcTemplate.queryForObject(query, String.class, email);
        } catch (Exception ex) {
            throw new DBNotFoundException("Error fetching password for email " + email + ": " + ex.getMessage());
        }
    }
}
