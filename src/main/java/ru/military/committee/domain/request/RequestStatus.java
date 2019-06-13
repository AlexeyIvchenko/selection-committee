package ru.military.committee.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request_status")
public class RequestStatus {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte statusId;

    @Column(name = "status_name")
    private String statusName;

    @JsonIgnore
    @OneToMany(mappedBy = "requestStatus", fetch = FetchType.LAZY)
    private Set<Request> requests;

}
