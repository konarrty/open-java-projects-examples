package com.example.testbyport.utils;


import com.example.testbyport.enums.Month;

import java.time.LocalDate;

public class MonthUtils {

    public static Integer getStartRelativeToMonth(LocalDate startDate, Integer monthNumber) {

        Integer startDateMonth = startDate.getMonth().getValue();
        int dayOfMonth = startDate.getDayOfMonth();
        Month month = Month.byNumber(monthNumber);

        if (monthNumber.equals(startDateMonth))
            return determineTheDay(month, dayOfMonth);
        else if (startDateMonth < monthNumber)
            return 1;
        else
            return null;
    }

    public static Integer getEndRelativeToMonth(LocalDate endDate, Integer monthNumber) {

        Integer endDateMonth = endDate.getMonth().getValue();
        Month month = Month.byNumber(monthNumber);

        if (monthNumber.equals(endDateMonth))
            return endDate.getDayOfMonth();
        else if (endDateMonth > monthNumber)
            return month.getDays();
        else
            return null;
    }

    private static Integer determineTheDay(Month month, int dayOfMonth) {

        return dayOfMonth != month.getDays() ? ++dayOfMonth : null;
    }

}
