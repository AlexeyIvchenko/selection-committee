package ru.military.committee.repository;

import ru.military.committee.domain.employee.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserLogin(String userLogin);

    AppUser findByUserId(Long userId);
}
