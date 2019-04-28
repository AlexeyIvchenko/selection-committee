package com.example.committee.service;

import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Specialty;
import com.example.committee.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Specialty> getAllSpecialtiesInFaculty(Faculty faculty) {
        return this.specialtyRepository.findSpecialtiesByFaculty(faculty);
    }

    public List<Specialty> getAllSpecialties() {
        return this.specialtyRepository.findAll();
    }
}

