package ru.military.committee.service;

import ru.military.committee.domain.location.Address;
import ru.military.committee.repository.AddressRepository;
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
