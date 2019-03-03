package com.example.committee.domain.location;

import com.example.committee.domain.location.Address;
import com.example.committee.domain.location.Region;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class City {
    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    @Column(name = "city_name")
    private String cityName;

    @OneToMany (mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;
}
