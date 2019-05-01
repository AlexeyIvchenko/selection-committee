package com.example.committee.repository;

import com.example.committee.domain.request.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Byte> {

    RequestStatus findByStatusId(byte statusId);
}
