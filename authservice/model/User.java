package com.ncu.library.authservice.model;

public class User {

    String _Email;
    String _Password;
    String _Name;

    public User() 
    {

    }

    public User(String email, String password, String name) 
    {
        _Email = email;
        _Password = password;
        _Name = name;
    }
    public String get_Name() 
    {
        return _Name;
    }

    public void set_Name(String name) 
    {
        _Name = name;
    }

    public String get_Email() 
    {
        return _Email;
    }

    public void set_Email(String email) 
    {
        _Email = email;
    }

    public String get_Password() 
    {
        return _Password;
    }

    public void set_Password(String password)
    {
        _Password = password;
    }
}
