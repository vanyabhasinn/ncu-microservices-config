package com.ncu.library.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ncu.library.userservice.dto.UserDto;
import com.ncu.library.userservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users - for admin use
    @GetMapping("/allusers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID (return UserDto, not UserResponseDto)
    @GetMapping("/user/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Get ALL users by location (as list)
    @GetMapping("/user/location/{userlocation}")
    public ResponseEntity<List<UserDto>> getUsersByLocation(@PathVariable("userlocation") String userlocation) {
        List<UserDto> users = userService.getUsersByLocation(userlocation);
        return ResponseEntity.ok(users);
    }

    // Add new user (still return UserDto for client simplicity)
    @PostMapping("/user")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        UserDto savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
