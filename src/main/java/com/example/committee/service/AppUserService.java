package com.example.committee.service;

import com.example.committee.domain.employee.AppUser;
import com.example.committee.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser getUserByLogin(String userLogin) {
        return appUserRepository.findByUserLogin(userLogin);
    }

    public AppUser getUserById(Long userId) {
        return this.appUserRepository.findByUserId(userId);
    }

    public List<AppUser> getAllUsers() {
        return this.appUserRepository.findAll();
    }

    public void addUser(AppUser user) {
        this.appUserRepository.save(user);
    }
}
