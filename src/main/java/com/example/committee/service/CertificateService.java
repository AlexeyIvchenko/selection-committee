package com.example.committee.service;

import com.example.committee.domain.personal.Certificate;
import com.example.committee.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    @Autowired
    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public Certificate getCertificateByRecruitId(Long recruitId) {
        return this.certificateRepository.findCertificateByRecruitRecruitId(recruitId);
    }

    public void addCertificate(Certificate certificate) {
        this.certificateRepository.save(certificate);
    }
}
