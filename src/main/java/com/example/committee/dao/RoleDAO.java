package com.example.committee.dao;

import com.example.committee.domain.AppRole;
import com.example.committee.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class RoleDAO extends JdbcDaoSupport {
    @Autowired
    public RoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<AppRole> getRolesByUserId(Long userId) {
        String sql = "select * from roles r inner join user_role ur on r.role_id = ur.role_id where ur.user_id = ?";
        Object[] params = new Object[]{userId};
        List<AppRole> userRoleList = this.getJdbcTemplate().query(sql, params, new RoleMapper());
        return userRoleList;
    }

    public List<AppRole> getAllRoles() {
        String sql = "select * from roles";
        List<AppRole> roleList = this.getJdbcTemplate().query(sql, new RoleMapper());
        return roleList;
    }

    public void setRolesToUser(Long userId, List<Long> roleIdList) {
        String sql = "insert into admissioncommittee.user_role (user_id, role_id) values (?, ?)";

        for (Long roleId : roleIdList) {
            this.getJdbcTemplate().update(sql, userId, roleId);
        }
    }
}
