package com.devmo.autogradingbe.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String ZONE_STRING = "Europe/Paris";

    public static LocalDateTime ldtNow() {
        return LocalDateTime.now(ZoneId.of(ZONE_STRING));
    }

    public static LocalDate ldNow() {
        return LocalDate.now(ZoneId.of(ZONE_STRING));
    }

    public static String formatLt(LocalTime lt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return lt.format(formatter);
    }

    public static String formatLd(LocalDate ld) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return ld.format(formatter);
    }
}
