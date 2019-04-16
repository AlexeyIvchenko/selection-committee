package com.example.committee.domain.personal;

import com.example.committee.domain.personal.Recruit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @Column(name = "certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;
    @Column(name = "certificate_number")
    private String certificateNumber;
    @Column(name = "score_ruslang")
    private short scoreRusLang;
    @Column(name = "score_math")
    private short scoreMath;
    @Column(name = "score_physics")
    private short scorePhysics;
    @Column(name = "score_social")
    private short scoreSocial;
    @Column(name = "score_foreignlang")
    private short scoreForeignLang;
    @Column(name = "score_physicalculture")
    private short scorePhysicalCulture;
    @Column(name = "education_institution")
    private String educationInstitution;
    @Column(name = "graduation_year")
    private short graduationYear;

    @JsonIgnore
    @OneToOne(mappedBy = "certificate")
    private Recruit recruit;

    public Certificate(Long certificateId) {
        this.certificateId = certificateId;
    }
}
