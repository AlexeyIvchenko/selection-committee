package com.example.committee.domain.personal;

import com.example.committee.domain.location.Address;
import com.example.committee.domain.location.Office;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Request> requests;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_entrance_test_id")
    private ExtranceTest extranceTest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruit_platoon_id")
    private Platoon platoon;

    public int sumExamOrCertificateScoreByFaculty(Faculty faculty) {
        int resultScore = 0;
        boolean isSPO = true;

        if (faculty.getScoreMath() != -1) {
            resultScore += this.getExam().getScoreMath();
            isSPO = false;
        }
        if (faculty.getScoreRusLang() != -1) {
            resultScore += this.getExam().getScoreRusLang();
            isSPO = false;
        }
        if (faculty.getScorePhysics() != -1) {
            resultScore += this.getExam().getScorePhysics();
            isSPO = false;
        }
        if (faculty.getScoreForeignLang() != -1) {
            resultScore += this.getExam().getScoreForeignLang();
            isSPO = false;
        }
        if (faculty.getScoreHistory() != -1) {
            resultScore += this.getExam().getScoreHistory();
            isSPO = false;
        }
        if (faculty.getScoreSocial() != -1) {
            resultScore += this.getExam().getScoreSocial();
            isSPO = false;
        }
        if (faculty.getScoreLiterature() != -1) {
            resultScore += this.getExam().getScoreLiterature();
            isSPO = false;
        }

        if (isSPO) {
            resultScore = (this.getCertificate().getScoreRusLang() + this.getCertificate().getScoreMath()
                    + this.getCertificate().getScorePhysics() + this.getCertificate().getScoreSocial() +
                    this.getCertificate().getScoreForeignLang() + this.getCertificate().getScorePhysicalCulture()) / 6;
            //Умножение среднего балла на коэффициент значимости оценок в атестате (по сравнению с ФИЗО)
            resultScore *= 50;
        }

        return resultScore;
    }

    private short sumExtranceTestScore() {
        short resultScore = 0;
        resultScore += this.extranceTest.getHorizontal_bar() + this.extranceTest.getRun100m() + this.extranceTest.getRun3km();
        return resultScore;
    }

    public int sumTotalRecruitScore(Faculty faculty) {
        return sumExamOrCertificateScoreByFaculty(faculty) + sumExtranceTestScore();
    }
}
