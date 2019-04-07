package com.example.committee.domain.personal;

import com.example.committee.domain.personal.Recruit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @Column(name = "passport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passportId;
    @Column(name = "passport_number", unique = true)
    private String passportNumber;
    @Column(name = "passport_issuedBy")
    private String passportIssuedBy;
    @Column(name = "passport_date")
    private Date passportDate;
    @JsonIgnore
    @OneToOne(mappedBy = "passport")
    private Recruit recruit;
}