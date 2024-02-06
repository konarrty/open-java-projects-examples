package com.example.kpimanagment.enums;

import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public enum EPeriodType {
    YEAR("Год"),
    QUARTER_1("1-й квартал"), QUARTER_2("2-й квартал"), QUARTER_3("3-й квартал"), QUARTER_4("4-й квартал"),
    MONTH_1("1-й месяц"), MONTH_2("2-й месяц"), MONTH_3("3-й месяц"), MONTH_4("4-й месяц"), MONTH_5("5-й месяц"), MONTH_6("6-й месяц"),
    MONTH_7("7-й месяц"), MONTH_8("8-й месяц"), MONTH_9("9-й месяц"), MONTH_10("10-й месяц"), MONTH_11("11-й месяц"), MONTH_12("12-й месяц");

    public String typeName;

    EPeriodType(String typeName) {
        this.typeName = typeName;
    }

    public boolean isMonth() {

        return this.equals(MONTH_1) || this.equals(MONTH_2) || this.equals(MONTH_3) || this.equals(MONTH_4)
                || this.equals(MONTH_5) || this.equals(MONTH_6) || this.equals(MONTH_7) || this.equals(MONTH_8)
                || this.equals(MONTH_9) || this.equals(MONTH_10) || this.equals(MONTH_11) || this.equals(MONTH_12);
    }

    public boolean isQuarter() {

        return this.equals(QUARTER_1) || this.equals(QUARTER_2) || this.equals(QUARTER_3) || this.equals(QUARTER_4);
    }

    public boolean isYear() {

        return this.equals(YEAR);
    }


    public LocalDate toMonthBegin() {
        if (this.isMonth())
            return LocalDate.of(0, this.ordinal() - 4, 1);
        else if (this.isQuarter())
            return LocalDate.of(0, 3 * (this.ordinal() - 1) + 1, 1);
        else
            return LocalDate.of(0, 1, 1);

    }

}