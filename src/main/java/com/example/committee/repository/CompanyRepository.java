package com.example.committee.repository;

import com.example.committee.domain.personal.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByOwnerFacultyFacultyIdAndCreateYear(short facultyId, short year);

    Company findByCompanyId(Long companyId);
}
