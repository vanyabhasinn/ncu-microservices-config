package com.ncu.library.userservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.ncu.library.userservice.model.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Map DB columns to User model
        Integer id = rs.getInt("Id");                 // auto-generated in DB
        String userName = rs.getString("UserName");   // DB column
        String userLocation = rs.getString("UserLocation"); // DB column

        return new User(id, userName, userLocation);
    }
}
