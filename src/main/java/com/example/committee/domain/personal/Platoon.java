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
    @Column(name = "platoon_name")
    private String platoonName;

    @JsonIgnore
    @OneToMany(mappedBy = "platoon", fetch = FetchType.EAGER)
    private List<Recruit> recruits;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public Platoon(String platoonName) {
        this.platoonName = platoonName;
    }
}
