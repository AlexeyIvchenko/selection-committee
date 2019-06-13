package ru.military.committee.repository;

import ru.military.committee.domain.personal.ExtranceTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtranceTestRepository extends JpaRepository<ExtranceTest, Long> {
}
