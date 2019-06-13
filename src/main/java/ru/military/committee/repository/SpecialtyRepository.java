package ru.military.committee.repository;

import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Short> {
    List<Specialty> findSpecialtiesByFaculty(Faculty faculty);
}
