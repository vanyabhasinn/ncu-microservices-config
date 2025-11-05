package com.ncu.library.userservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncu.library.userservice.dto.UserDto;
import com.ncu.library.userservice.exception.DuplicateUserException;
import com.ncu.library.userservice.exception.InvalidUserDataException;
import com.ncu.library.userservice.exception.UserNotFoundException;
import com.ncu.library.userservice.irepository.iUserRepository;
import com.ncu.library.userservice.model.User;

@Service
public class UserService {

    private final iUserRepository userRepository;

    @Autowired
    public UserService(iUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("No users found in the database");
        }
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Updated: return all users for a location
    public List<UserDto> getUsersByLocation(String userLocation) {
        if (userLocation == null || userLocation.isBlank()) {
            throw new InvalidUserDataException("User location cannot be empty");
        }

        List<User> users = userRepository.getUsersByLocation(userLocation);
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("User(s) with location '" + userLocation + "' not found");
        }
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidUserDataException("Invalid user ID provided");
        }

        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return toDto(user);
    }

    public UserDto addUser(UserDto userDto) {
        if (userDto == null) {
            throw new InvalidUserDataException("User data cannot be null");
        }
        if (userDto.getUserName() == null || userDto.getUserName().isBlank()) {
            throw new InvalidUserDataException("Username cannot be empty");
        }
        if (userDto.getUserLocation() == null || userDto.getUserLocation().isBlank()) {
            throw new InvalidUserDataException("User location cannot be empty");
        }

        List<User> existingUsers = userRepository.getAllUsers();
        boolean duplicate = existingUsers.stream()
                .anyMatch(u -> u.getUserName().equalsIgnoreCase(userDto.getUserName()));
        if (duplicate) {
            throw new DuplicateUserException("Username '" + userDto.getUserName() + "' already exists");
        }

        User userEntity = toEntity(userDto);
        User saved = userRepository.addUser(userEntity);

        if (saved == null) {
            throw new InvalidUserDataException("Failed to add user due to database error");
        }

        return toDto(saved);
    }

    private UserDto toDto(User u) {
        if (u == null) return null;
        return new UserDto(u.getId(), u.getUserName(), u.getUserLocation());
    }

    private User toEntity(UserDto dto) {
        if (dto == null) return null;
        User u = new User();
        u.setUserName(dto.getUserName());
        u.setUserLocation(dto.getUserLocation());
        return u;
    }
}
