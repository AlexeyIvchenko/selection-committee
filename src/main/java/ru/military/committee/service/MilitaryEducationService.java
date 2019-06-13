package ru.military.committee.service;

import ru.military.committee.domain.request.MilitaryEducation;
import ru.military.committee.repository.MilitaryEducationRepository;
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
