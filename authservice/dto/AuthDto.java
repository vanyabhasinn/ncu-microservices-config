package com.ncu.library.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthDto {

    @JsonProperty("email")
    String _Email;

    @JsonProperty("password")
    String _Password;

    public AuthDto() {
    }

    public AuthDto(String email, String password) {
        _Email = email;
        _Password = password;
    }

    // ✅ Updated getter/setter names
    public String getEmail() {
        return _Email;
    }

    public void setEmail(String email) {
        _Email = email;
    }

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String password) {
        _Password = password;
    }
}
