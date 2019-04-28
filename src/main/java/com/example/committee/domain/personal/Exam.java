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
@Table(name = "unified_state_exam")
public class Exam {
    @Id
    @Column(name = "exam_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examId;
    @Column(name = "score_math")
    private byte scoreMath;
    @Column(name = "score_ruslang")
    private byte scoreRusLang;
    @Column(name = "score_physics")
    private byte scorePhysics;
    @Column(name = "score_foreignlang")
    private byte scoreForeignLang;
    @Column(name = "score_history")
    private byte scoreHistory;
    @Column(name = "score_social")
    private byte scoreSocial;
    @Column(name = "score_literature")
    private byte scoreLiterature;
    @Column(name = "exam_year")
    private short examYear;

    @JsonIgnore
    @OneToOne(mappedBy = "exam")
    private Recruit recruit;

    public Exam(Long examId) {
        this.examId = examId;
    }
}
