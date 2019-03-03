package com.example.committee.repository;

import com.example.committee.domain.personal.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, String> {
}
