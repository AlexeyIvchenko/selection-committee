package com.example.committee.domain.personal;

import com.example.committee.domain.personal.Recruit;
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

    @OneToMany (mappedBy = "nationality", fetch = FetchType.LAZY)
    private Set<Recruit> recruits;
}
