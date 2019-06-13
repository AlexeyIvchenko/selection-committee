package ru.military.committee.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @Min(0)
    @Max(100)
    private byte scoreMath;

    @Column(name = "score_ruslang")
    @NotNull
    @Min(0)
    @Max(100)
    private byte scoreRusLang;

    @Column(name = "score_physics")
    @Min(0)
    @Max(100)
    private byte scorePhysics;

    @Column(name = "score_foreignlang")
    @Min(0)
    @Max(100)
    private byte scoreForeignLang;

    @Column(name = "score_history")
    @Min(0)
    @Max(100)
    private byte scoreHistory;

    @Column(name = "score_social")
    @Min(0)
    @Max(100)
    private byte scoreSocial;

    @Column(name = "score_literature")
    @Min(0)
    @Max(100)
    private byte scoreLiterature;

    @Column(name = "exam_year")
    private short examYear;

    @JsonIgnore
    @OneToOne(mappedBy = "exam")
    private Recruit recruit;
}
