package com.example.committee.service;

import com.example.committee.domain.employee.AppRole;
import com.example.committee.repository.AppRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;

    public AppRoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public List<AppRole> getAllRoles() {
        return this.appRoleRepository.findAll();
    }
}
