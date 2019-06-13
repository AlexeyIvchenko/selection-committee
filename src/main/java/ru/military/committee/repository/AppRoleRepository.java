package ru.military.committee.repository;

import ru.military.committee.domain.employee.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

}
