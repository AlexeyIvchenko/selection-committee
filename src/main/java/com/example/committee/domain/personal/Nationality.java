package com.example.committee.domain.personal;

import com.example.committee.domain.personal.Recruit;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nationality")
public class Nationality {
    @Id
    @Column(name = "nationality_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nationalityId;
    @Column(name = "nationality_name")
    private String nationalityName;

    @JsonIgnore
    @OneToMany(mappedBy = "nationality", fetch = FetchType.LAZY)
    private Set<Recruit> recruits;
}
