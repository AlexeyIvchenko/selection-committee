package com.example.committee.domain.personal;

import com.example.committee.domain.location.Office;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Company {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @JsonIgnore
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Platoon> platoons;
}
