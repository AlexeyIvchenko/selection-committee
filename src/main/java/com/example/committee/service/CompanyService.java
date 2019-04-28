package com.example.committee.service;

import com.example.committee.domain.personal.Company;
import com.example.committee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void addCompany(Company company) {
        this.companyRepository.save(company);
    }

    public List<Company> getCompaniesByFacultyAndYear(short facultyId, short year) {
        return this.companyRepository.findByOwnerFacultyFacultyIdAndCreateYear(facultyId, year);
    }

    public Company getCompanyById(Long companyId) {
        return this.companyRepository.findByCompanyId(companyId);
    }
}
