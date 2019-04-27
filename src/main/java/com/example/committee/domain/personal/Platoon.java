package com.example.committee.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "platoon")
public class Platoon {
    @Id
    @Column(name = "platoon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long platoonId;

    @Column(name = "platoon_number")
    private short platoonNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "platoon", fetch = FetchType.EAGER)
    private List<Recruit> recruits;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(platoonNumber).append(" ").append("взвод ").append(company.getCompanyNumber()).append(" ")
                .append(" роты ").append("факультета ").append(company.getOwnerFaculty().getFacultyName());
        return sb.toString();
    }
}
