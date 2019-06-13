package ru.military.committee.utils;

import java.util.Collections;
import java.util.List;

public class Sorter {

    public static List<RequestAndScore> sortRequestAndScoreListByScore(List<RequestAndScore> requestAndScoreList) {
        Collections.sort(requestAndScoreList, (ras1, ras2) -> {
            Integer score1 = Integer.valueOf(ras1.getScore());
            Integer score2 = Integer.valueOf(ras2.getScore());
            int scoreComp = score2.compareTo(score1);
            if (scoreComp != 0) {
                return scoreComp;
            } else {
                Integer examScore1 = Integer.valueOf(ras1.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras1.getRequest().getSpecialty().getFaculty()));
                Integer examScore2 = Integer.valueOf(ras2.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras2.getRequest().getSpecialty().getFaculty()));
                return examScore2.compareTo(examScore1);
            }
        });
        return requestAndScoreList;
    }

    public static List<RequestAndScore> sortRequestAndScoreListByPriorityAndScore(List<RequestAndScore> requestAndScoreList) {
        Collections.sort(requestAndScoreList, (ras1, ras2) -> {
            Integer priority1 = Integer.valueOf(ras1.getRequest().getPriority());
            Integer priority2 = Integer.valueOf(ras2.getRequest().getPriority());
            int priorityComp = priority1.compareTo(priority2);
            if (priorityComp != 0) {
                return priorityComp;
            } else {
                Integer score1 = Integer.valueOf(ras1.getScore());
                Integer score2 = Integer.valueOf(ras2.getScore());
                int scoreComp = score2.compareTo(score1);
                if (scoreComp != 0) {
                    return scoreComp;
                } else {
                    Integer examScore1 = Integer.valueOf(ras1.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras1.getRequest().getSpecialty().getFaculty()));
                    Integer examScore2 = Integer.valueOf(ras2.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(ras2.getRequest().getSpecialty().getFaculty()));
                    return examScore2.compareTo(examScore1);
                }
            }
        });
        return requestAndScoreList;
    }
}
