package ru.military.committee.utils;

import java.util.*;

public class DateWorker {
    public static short getCurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());

        return (short) calendar.get(Calendar.YEAR);
    }

    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(new Date().getTime());
    }

    public static List<Integer> getValidYears() {
        List<Integer> validExamYears = new ArrayList<>();
        int currentYear = getCurrentYear();
        for (int i = currentYear; i > currentYear - 5; i--) {
            validExamYears.add(i);
        }
        return validExamYears;
    }

    public static java.sql.Date getFirstDateInCurrentYear() {
        String stringDate = getCurrentYear() + "-01" + "-01";
        return java.sql.Date.valueOf(stringDate);
    }
}
