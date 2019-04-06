package com.example.committee.repository;

import com.example.committee.domain.location.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findAddressByRecruitRecruitId(Long recruitId);
}
