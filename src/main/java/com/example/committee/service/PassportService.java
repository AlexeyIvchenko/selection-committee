package com.example.committee.service;

import com.example.committee.domain.personal.Passport;
import com.example.committee.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {
    @Autowired
    private final PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public void addPassport(Passport passport) {
        this.passportRepository.save(passport);
    }
}