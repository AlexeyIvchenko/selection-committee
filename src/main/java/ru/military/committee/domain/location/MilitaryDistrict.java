package ru.military.committee.domain.location;

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
@Table(name = "military_district")
public class MilitaryDistrict {
    @Id
    @Column(name = "district_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short districtId;
    private String districtName;

    @JsonIgnore
    @OneToMany(mappedBy = "militaryDistrict", fetch = FetchType.LAZY)
    private Set<Region> regions;
}
