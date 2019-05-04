package com.example.committee.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "entrance_test")
public class ExtranceTest {
    @Id
    @Column(name = "entrance_test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extranceTestId;
    @Column(name = "run100m")
    private byte run100m;
    @Column(name = "run3km")
    private byte run3km;
    @Column(name = "horizontal_bar")
    private byte horizontal_bar;
    @Column(name = "prof_group")
    private byte prof_group;

    @JsonIgnore
    @OneToOne(mappedBy = "extranceTest")
    private Recruit recruit;

    public short sumScoreFIZO() {
        return (short) (run100m + run3km + horizontal_bar);
    }

}
