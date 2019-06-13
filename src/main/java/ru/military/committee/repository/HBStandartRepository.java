package ru.military.committee.repository;

import ru.military.committee.domain.standarts.HBStandart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HBStandartRepository extends JpaRepository<HBStandart, Byte> {
    HBStandart findByResult(Byte result);

    HBStandart findByScore(Byte score);
}
