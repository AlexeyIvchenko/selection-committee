package com.example.committee.domain.location;

import com.example.committee.domain.personal.Recruit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "block_number")
    @Min(1)
    @Max(99)
    private int blockNumber;

    @Column(name = "apartment_number")
    @Min(1)
    @Max(999)
    private int apartmentNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Recruit recruit;
}
