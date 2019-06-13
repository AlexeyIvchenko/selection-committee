package ru.military.committee.service;

import ru.military.committee.domain.request.RequestStatus;
import ru.military.committee.repository.RequestStatusRepository;
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
