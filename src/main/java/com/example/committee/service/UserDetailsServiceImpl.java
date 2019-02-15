package com.example.committee.service;

import com.example.committee.dao.RoleDAO;
import com.example.committee.dao.UserDAO;
import com.example.committee.domain.AppRole;
import com.example.committee.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = this.userDAO.getUserByLogin(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        List<AppRole> roleList = this.roleDAO.getRolesByUserId(user.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleList != null) {
            for (AppRole role : roleList) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(user.getUserLogin(), //
                user.getUserPassword(), grantList);

        return userDetails;
    }
}
