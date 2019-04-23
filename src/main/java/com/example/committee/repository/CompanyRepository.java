package com.example.committee.repository;

import com.example.committee.domain.personal.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
