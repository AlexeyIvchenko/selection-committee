package com.example.committee.domain.request;

import com.example.committee.domain.request.Faculty;
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
@Table(name = "military_education")
public class MilitaryEducation {
    @Id
    @Column(name = "education_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short educationId;
    @Column(name = "education_name")
    private String educationName;
    @OneToMany (mappedBy = "education", fetch = FetchType.EAGER)
    private Set<Faculty> faculties;
}
