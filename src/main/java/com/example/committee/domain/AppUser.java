package com.example.committee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Long userId;
    private String userLogin;
    private String userPassword;
    private String userSurname;
    private String userName;
    private String userSecondName;
    private boolean online;
    private List<AppRole> rolesList;

    public AppUser(String userLogin, String userPassword, boolean online) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.online = online;
    }

    public AppUser(Long userId, String userLogin, String userPassword, String userSurname, String userName, String userSecondName, boolean online) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userSurname = userSurname;
        this.userName = userName;
        this.userSecondName = userSecondName;
        this.online = online;
    }
}

