package ru.military.committee.service;

import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.MilitaryEducation;
import ru.military.committee.repository.FacultyRepository;
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

    public void addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }
}
