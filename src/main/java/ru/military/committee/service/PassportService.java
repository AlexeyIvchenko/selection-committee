package ru.military.committee.service;

import ru.military.committee.domain.personal.Passport;
import ru.military.committee.repository.PassportRepository;
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

    public Passport getPassportByRecruitId(Long recruitId) {
        return this.passportRepository.findPassportByRecruitRecruitId(recruitId);
    }
}