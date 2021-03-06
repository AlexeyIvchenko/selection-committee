package ru.military.committee.service;

import ru.military.committee.domain.employee.AppRole;
import ru.military.committee.repository.AppRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;

    public AppRoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public List<AppRole> getAllRoles() {
        return this.appRoleRepository.findAll();
    }

    public Optional<AppRole> getRoleById(Long roleId) {
        return this.appRoleRepository.findById(roleId);
    }
}
