package ru.military.committee.utils;

import ru.military.committee.domain.personal.Recruit;
import ru.military.committee.domain.request.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportHelper {
    private String currentDate;
    private String faculty;
    private String specialty;
    private String number;
    private String fio;
    private String birthday;
    private String office;
    private String prof_group;
    private String scoreMath;
    private String scoreRusLang;
    private String scorePhysics;
    private String scoreForeignLang;
    private String scoreHistory;
    private String scoreSocial;
    private String scoreLiterature;
    private String scoreFizo;
    private String totalScore;
    private String averageCertificateScore;
    private String decision;

    public ReportHelper(Request request, int number) {
        Recruit recruit = request.getRecruit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.currentDate = dateFormat.format(DateWorker.getCurrentDate()) + "г.";
        this.faculty = request.getSpecialty().getFaculty().getFacultyName();
        this.specialty = request.getSpecialty().getSpecialtyName();
        this.number = String.valueOf(number);
        this.fio = recruit.getSurname() + " " + recruit.getName() + " " + recruit.getSecondName();
        this.birthday = dateFormat.format(recruit.getBirthday()) + "г.";
        this.office = recruit.getOffice().getOfficeName();
        this.prof_group = String.valueOf(recruit.getExtranceTest().getProf_group());
        this.scoreMath = String.valueOf(recruit.getExam().getScoreMath());
        this.scoreRusLang = String.valueOf(recruit.getExam().getScoreRusLang());
        this.scorePhysics = String.valueOf(recruit.getExam().getScorePhysics());
        this.scoreForeignLang = String.valueOf(recruit.getExam().getScoreForeignLang());
        this.scoreHistory = String.valueOf(recruit.getExam().getScoreHistory());
        this.scoreSocial = String.valueOf(recruit.getExam().getScoreSocial());
        this.scoreLiterature = String.valueOf(recruit.getExam().getScoreLiterature());
        this.scoreFizo = String.valueOf(recruit.getExtranceTest().sumScoreFIZO());
        this.totalScore = String.valueOf(recruit.sumTotalRecruitScore(request.getSpecialty().getFaculty()));
        this.averageCertificateScore = String.valueOf(recruit.getAverageCertificateScore());
        this.decision = request.getRequestStatus().getStatusName();
    }

}
