package com.example.testbyport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Month {

    JANUARY("Январь", 31, 1),
    FEBRUARY("Февраль", 28, 2);

    private final String name;
    private final Integer days;
    private final Integer number;

    public static List<Month> asList() {

        return Arrays.stream(Month.values()).toList();
    }

    public static Month byNumber(Integer number) {

        return Arrays.stream(Month.values())
                .filter(m -> m.getNumber().equals(number))
                .findFirst().orElse(null);

    }
}
