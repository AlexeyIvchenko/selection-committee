package com.example.committee.service;

import com.example.committee.domain.request.RequestStatus;
import com.example.committee.repository.RequestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStatusService {

    @Autowired
    private final RequestStatusRepository requestStatusRepository;

    public RequestStatusService(RequestStatusRepository requestStatusRepository) {
        this.requestStatusRepository = requestStatusRepository;
    }

    public RequestStatus getRequestStatusById(byte statusId) {
        return this.requestStatusRepository.findByStatusId(statusId);
    }
}
