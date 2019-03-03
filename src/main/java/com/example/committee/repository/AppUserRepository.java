package com.example.committee.repository;

import com.example.committee.domain.employee.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserLogin(String userLogin);

    AppUser findByUserId(Long userId);
}
