package ru.military.committee.domain.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<City> cities;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "military_district_id")
    private MilitaryDistrict militaryDistrict;

    @JsonIgnore
    @OneToMany (mappedBy = "region", fetch = FetchType.LAZY)
    private Set<Office> offices;
}
