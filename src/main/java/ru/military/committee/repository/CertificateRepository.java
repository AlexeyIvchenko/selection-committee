package ru.military.committee.repository;

import ru.military.committee.domain.personal.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    Certificate findCertificateByRecruitRecruitId(Long recruitId);
}
