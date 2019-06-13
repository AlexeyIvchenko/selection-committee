package ru.military.committee.repository;

import ru.military.committee.domain.location.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findAddressByRecruitRecruitId(Long recruitId);
}
