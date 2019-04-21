package com.example.committee.domain.personal;

import com.example.committee.domain.request.Request;
import com.example.committee.domain.location.Address;
import com.example.committee.domain.location.Office;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recruit")
public class Recruit {
    @Id
    @Column(name = "recruit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId;
    @Column(name = "recruit_surname")
    private String surname;
    @Column(name = "recruit_name")
    private String name;
    @Column(name = "recruit_secondname")
    private String secondName;
    @Column(name = "recruit_birthday")
    private Date birthday;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "recruit_passport_id")
    private Passport passport;

    @Column(name = "recruit_sex")
    private boolean sex;
    @Column(name = "recruit_family_status")
    private boolean familyStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_nationality_id")
    private Nationality nationality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_office_id")
    private Office office;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_exam_id")
    private Exam exam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_certificate_id")
    private Certificate certificate;

    @OneToMany(mappedBy = "recruit", fetch = FetchType.EAGER)
    private List<Request> requests;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_entrance_test_id")
    private ExtranceTest extranceTest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_platoon_id")
    private Platoon platoon;
}
