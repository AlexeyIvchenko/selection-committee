package ru.military.committee.repository;

import ru.military.committee.domain.request.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Byte> {

    RequestStatus findByStatusId(byte statusId);
}
