package ru.military.committee.repository;

import ru.military.committee.domain.personal.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality, Long> {
}
