package ru.military.committee.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    @Size(min = 16, max = 16)
    private String certificateNumber;

    @Column(name = "score_ruslang")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scoreRusLang;

    @Column(name = "score_math")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scoreMath;

    @Column(name = "score_physics")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scorePhysics;

    @Column(name = "score_social")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scoreSocial;

    @Column(name = "score_foreignlang")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scoreForeignLang;

    @Column(name = "score_physicalculture")
    @NotNull
    @Min(2)
    @Max(5)
    private byte scorePhysicalCulture;

    @Column(name = "education_institution")
    @Size(max = 128)
    private String educationInstitution;

    @Column(name = "graduation_year")
    private short graduationYear;

    @JsonIgnore
    @OneToOne(mappedBy = "certificate")
    private Recruit recruit;
}
