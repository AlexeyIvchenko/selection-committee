package ru.military.committee.repository;

import ru.military.committee.domain.personal.Company;
import ru.military.committee.domain.personal.Platoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoonRepository extends JpaRepository<Platoon, Long> {
    List<Platoon> findAllByCompanyIn(List<Company> companies);

    List<Platoon> findAllByCompany(Company company);
}
