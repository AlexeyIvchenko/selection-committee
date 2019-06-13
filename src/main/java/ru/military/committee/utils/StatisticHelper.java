package ru.military.committee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticHelper {
    private String militaryDistrictName;
    private String civilMaleCount;
    private String contractMaleCount;
    private String conscriptMaleCount;

    private String civilFemaleCount;
    private String contractFemaleCount;
    private String conscriptFemaleCount;

    private String currentDate;

    public void setCurrentDate(Date currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.currentDate = dateFormat.format(currentDate) + "г.";
    }
}
