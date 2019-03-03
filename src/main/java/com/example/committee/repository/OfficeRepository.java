package com.example.committee.repository;

import com.example.committee.domain.location.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
