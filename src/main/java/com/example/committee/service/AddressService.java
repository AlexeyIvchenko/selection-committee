package com.example.committee.service;

import com.example.committee.domain.location.Address;
import com.example.committee.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddressByRecruitId(Long recruitId) {
        return this.addressRepository.findAddressByRecruitRecruitId(recruitId);
    }

    public void addAddress(Address address) {
        this.addressRepository.save(address);
    }
}
