package com.example.committee.domain.location;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "region")
public class Region {
    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;
    @Column(name = "region_name")
    private String regionName;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<City> cities;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "military_district_id")
    private MilitaryDistrict militaryDistrict;

    @OneToMany (mappedBy = "region", fetch = FetchType.LAZY)
    private Set<Office> offices;
}
