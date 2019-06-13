package ru.military.committee.repository;

import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.MilitaryEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Short> {
    List<Faculty> findFacultiesByEducation(MilitaryEducation education);
}
