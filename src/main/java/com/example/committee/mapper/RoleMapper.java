package com.example.committee.mapper;

import com.example.committee.domain.AppRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<AppRole> {

    @Override
    public AppRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long roleId = rs.getLong("role_id");
        String roleName = rs.getString("role_name");
        return new AppRole(roleId, roleName);
    }
}
