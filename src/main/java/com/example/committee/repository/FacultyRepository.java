package com.example.committee.repository;

import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.MilitaryEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Short> {
    List<Faculty> findFacultiesByEducation(MilitaryEducation education);
}
