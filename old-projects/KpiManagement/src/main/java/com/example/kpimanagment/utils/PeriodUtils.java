package com.example.kpimanagment.utils;

import com.example.kpimanagment.enums.EPeriodType;
import com.example.kpimanagment.model.Period;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PeriodUtils {

    public Period parsePeriod(LocalDate date) {

        return new Period(date.getYear(), date.getMonth().getValue());
    }

    public boolean isBeforeLocaleDate(Period period) {
        LocalDate date = LocalDate.now();
        Period nowPeriod = parsePeriod(date);

        if (period.getYear() > nowPeriod.getYear())
            return false;
        else if (period.getYear() < nowPeriod.getYear())
            return true;
        else {
            EPeriodType type = period.getType();
            return type.toMonthBegin().plusYears(period.getYear()).isBefore(date)
                    || type.toMonthBegin().plusYears(period.getYear()).isEqual(date);
        }

    }

    public boolean isBeforeDate(Period period, LocalDate date) {

        Period nowPeriod = parsePeriod(date);

        if (period.getYear() > nowPeriod.getYear())
            return false;
        else if (period.getYear() < nowPeriod.getYear())
            return true;
        else {
            EPeriodType type = period.getType();
            return type.toMonthBegin().plusYears(period.getYear()).isBefore(date)
                    || type.toMonthBegin().plusYears(period.getYear()).isEqual(date);
        }

    }

    public boolean isAfterDate(Period period, LocalDate date) {

        Period nowPeriod = parsePeriod(date);

        if (period.getYear() > nowPeriod.getYear())
            return false;
        else if (period.getYear() < nowPeriod.getYear())
            return true;
        else {
            EPeriodType type = period.getType();
            return type.toMonthBegin().plusYears(period.getYear()).isAfter(date)
                    || type.toMonthBegin().plusYears(period.getYear()).isEqual(date);
        }

    }


    public boolean isBefore(Period period, Period period2) {

        if (period.getYear() > period2.getYear())
            return false;
        else if (period.getYear() < period2.getYear())
            return true;
        else {
            EPeriodType type = period.getType();
            EPeriodType type2 = period2.getType();
            return type.toMonthBegin().plusYears(period.getYear()).isBefore(type2.toMonthBegin().plusYears(period.getYear()))
                    || type.toMonthBegin().plusYears(period.getYear()).isEqual(type2.toMonthBegin().plusYears(period.getYear()));
        }

    }
}
