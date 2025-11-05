package com.ncu.library.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignupDto {

    @JsonProperty("email")
    String _Email;

    @JsonProperty("password")
    String _Password;

    @JsonProperty("name")
    String _Name;

    public SignupDto() 
    {
    }

    public SignupDto(String email, String password, String name) 
    {
        _Email = email;
        _Password = password;
        _Name = name;
    }

   
    public String getEmail() 
    {
        return _Email;
    }

   
    public void setEmail(String email) 
    {
        _Email = email;
    }

    public String getPassword() 
    {
        return _Password;
    }

    public void setPassword(String password) 
    {
        _Password = password;
    }

    public String getName() 
    {
        return _Name;
    }

    public void setName(String name) 
    {
        _Name = name;
    }
}
