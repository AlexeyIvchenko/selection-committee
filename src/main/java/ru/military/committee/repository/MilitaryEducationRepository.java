package ru.military.committee.repository;

import ru.military.committee.domain.request.MilitaryEducation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilitaryEducationRepository extends JpaRepository<MilitaryEducation, Short> {
}
