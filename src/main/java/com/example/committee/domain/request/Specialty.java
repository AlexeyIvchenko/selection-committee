package com.example.committee.domain.request;

import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
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
@Table(name = "specialty")
public class Specialty {
    @Id
    @Column(name = "specialty_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short specialtyId;
    @Column(name = "specialty_name")
    private String specialtyName;

    @OneToMany(mappedBy = "specialty", fetch = FetchType.LAZY)
    private Set<Request> requests;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
