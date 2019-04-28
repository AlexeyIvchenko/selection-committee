package com.example.committee.repository;

import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Short> {
    List<Specialty> findSpecialtiesByFaculty(Faculty faculty);
}
