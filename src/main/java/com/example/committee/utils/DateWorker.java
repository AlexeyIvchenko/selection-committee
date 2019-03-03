package com.example.committee.utils;

import java.util.*;

public class DateWorker {
    private static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());

        return calendar.get(Calendar.YEAR);
    }

    public static List<Integer> getValidExamYears() {
        List<Integer> validExamYears = new ArrayList<>();
        int currentYear = getCurrentYear();
        for (int i = currentYear; i > currentYear - 5; i--) {
            validExamYears.add(i);
        }
        return validExamYears;
    }
}
