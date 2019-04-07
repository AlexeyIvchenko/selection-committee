package com.example.committee.repository;

import com.example.committee.domain.request.MilitaryEducation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilitaryEducationRepository extends JpaRepository<MilitaryEducation, Short> {
}
