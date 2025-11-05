package com.ncu.library.userservice.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ncu.library.userservice.irepository.iUserRepository;
import com.ncu.library.userservice.model.User;

@Repository("UserRepository")
public class UserRepository implements iUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE Id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Updated: fetch all users by location
    @Override
    public List<User> getUsersByLocation(String userLocation) {
        String sql = "SELECT * FROM users WHERE LOWER(UserLocation) = LOWER(?)";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper(), userLocation);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (UserName, UserLocation) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserLocation());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                user.setId(keyHolder.getKey().intValue());
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
