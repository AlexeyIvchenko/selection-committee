package ru.military.committee.repository;

import ru.military.committee.domain.personal.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, String> {
    Passport findPassportByRecruitRecruitId(Long recruitId);
}
