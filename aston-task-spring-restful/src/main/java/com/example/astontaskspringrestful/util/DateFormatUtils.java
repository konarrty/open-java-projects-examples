package com.example.astontaskspringrestful.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {

    public static String format(LocalDateTime dateTime) {
        String pattern = "dd.MM.yyyy HH:mm z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return formatter.format(ZonedDateTime.of(dateTime, ZoneId.of("UTC+3")));
    }
}
