package ru.military.committee.service;

import ru.military.committee.domain.employee.AppUser;
import ru.military.committee.repository.AppUserRepository;
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

    public void deleteUserById(Long userId) {
        this.appUserRepository.deleteById(userId);
    }
}
