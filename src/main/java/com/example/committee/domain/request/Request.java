package com.example.committee.domain.request;

import com.example.committee.domain.personal.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request")
public class Request {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "priority")
    private short priority;
    @Column(name = "request_date")
    private Date requestDate;

    public Request(Recruit recruit, Specialty specialty, short priority, Date requestDate) {
        this.recruit = recruit;
        this.specialty = specialty;
        this.priority = priority;
        this.requestDate = requestDate;
    }
}
