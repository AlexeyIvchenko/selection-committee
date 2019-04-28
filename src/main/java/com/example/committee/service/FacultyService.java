package com.example.committee.service;

import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.MilitaryEducation;
import com.example.committee.domain.request.Specialty;
import com.example.committee.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFacultiesInMilitaryEducation(MilitaryEducation education) {
        return this.facultyRepository.findFacultiesByEducation(education);
    }

    public List<Faculty> getAllFaculties() {
        return this.facultyRepository.findAll();
    }

    public Faculty getFacultyById(short facultyId) {
        return this.facultyRepository.findById(facultyId).orElse(null);
    }
}
