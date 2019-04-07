package com.example.committee.service;

import com.example.committee.domain.request.MilitaryEducation;
import com.example.committee.repository.MilitaryEducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryEducationService {
    private final MilitaryEducationRepository militaryEducationRepository;

    public MilitaryEducationService(MilitaryEducationRepository militaryEducationRepository) {
        this.militaryEducationRepository = militaryEducationRepository;
    }

    public List<MilitaryEducation> getAllMilitaryEducations() {
        return this.militaryEducationRepository.findAll();
    }

    public MilitaryEducation getMilitaryEducationById(short educationId) {
        return this.militaryEducationRepository.findById(educationId).orElse(null);
    }
}
