package ru.military.committee.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(min = 12, max = 12)
    private String passportNumber;

    @Column(name = "passport_issuedBy")
    @NotNull
    private String passportIssuedBy;

    @Column(name = "passport_date")
    @NotNull
    private Date passportDate;

    @JsonIgnore
    @OneToOne(mappedBy = "passport")
    private Recruit recruit;
}