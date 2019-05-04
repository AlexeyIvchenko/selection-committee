package com.example.committee.repository;

import com.example.committee.domain.request.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Byte> {

    RequestStatus findByStatusId(byte statusId);
}
