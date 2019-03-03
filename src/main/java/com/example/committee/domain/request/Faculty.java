package com.example.committee.domain.request;

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
@Table(name = "faculty")
public class Faculty {
    @Id
    @Column(name = "faculty_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short facultyId;
    @Column(name = "faculty_name")
    private String facultyName;
    @Column(name = "girl_access")
    private boolean girlAccess;
    @Column(name = "score_math")
    private short scoreMath;
    @Column(name = "score_ruslang")
    private short scoreRusLang;
    @Column(name = "score_physics")
    private short scorePhysics;
    @Column(name = "score_foreignlang")
    private short scoreForeignLang;
    @Column(name = "score_history")
    private short scoreHistory;
    @Column(name = "score_social")
    private short scoreSocial;
    @Column(name = "score_literature")
    private short scoreLiterature;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.EAGER)
    private Set<Specialty> specialties;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "military_education_id")
    private MilitaryEducation education;
}
