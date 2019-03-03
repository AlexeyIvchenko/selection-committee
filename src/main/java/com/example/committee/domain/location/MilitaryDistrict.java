package com.example.committee.domain.location;

import com.example.committee.domain.location.Region;
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

    @OneToMany(mappedBy = "militaryDistrict", fetch = FetchType.LAZY)
    private Set<Region> regions;
}
