package ru.military.committee.domain.location;

import ru.military.committee.domain.personal.Recruit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "office")
public class Office {
    @Id
    @Column(name = "office_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;
    @Column(name = "office_name")
    private String officeName;

    @JsonIgnore
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
    private Set<Recruit> recruits;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;
}
