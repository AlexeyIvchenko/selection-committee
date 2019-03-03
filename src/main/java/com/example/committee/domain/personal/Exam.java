package com.example.committee.domain.personal;

import com.example.committee.domain.personal.Recruit;
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
    @Column(name = "exam_year")
    private short examYear;

    @OneToOne(mappedBy = "exam")
    private Recruit recruit;
}
