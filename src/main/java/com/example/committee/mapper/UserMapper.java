package com.example.committee.mapper;

import com.example.committee.domain.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String userLogin = resultSet.getString("user_login");
        String userPassword = resultSet.getString("user_password");
        String userSurname = resultSet.getString("user_surname");
        String userName = resultSet.getString("user_name");
        String userSecondName = resultSet.getString("user_secondname");
        boolean isOnline = resultSet.getBoolean("online_status");
        return new AppUser(userId, userLogin, userPassword, userSurname, userName, userSecondName, isOnline);
    }
}
