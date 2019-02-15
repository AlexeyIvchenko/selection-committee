package com.example.committee.dao;

import com.example.committee.domain.AppRole;
import com.example.committee.domain.AppUser;
import com.example.committee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport {
    @Autowired
    private RoleDAO roleDAO;

    public UserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser getUserByLogin(String userLogin) {
        String sql = "select * from usert u where u.user_login = ?";
        Object[] params = new Object[]{userLogin};
        try {
            AppUser user = this.getJdbcTemplate().queryForObject(sql, params, new UserMapper());
            List<AppRole> userRoleList = roleDAO.getRolesByUserId(user.getUserId());
            user.setRolesList(new ArrayList<>(userRoleList));
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<AppUser> getUsersList() {
        String sql = "select * from usert";
        List<AppUser> userList = this.getJdbcTemplate().query(sql, new UserMapper());
        for (AppUser user : userList) {
            List<AppRole> userRoleList = roleDAO.getRolesByUserId(user.getUserId());
            user.setRolesList(new ArrayList<>(userRoleList));
        }
        return userList;
    }

    public void addUser(AppUser user) {
        String sql = "insert into admissioncommittee.usert (user_login, user_password, online_status) values (?, ?, 0)";
        this.getJdbcTemplate().update(sql, user.getUserLogin(), user.getUserPassword());
    }
}
