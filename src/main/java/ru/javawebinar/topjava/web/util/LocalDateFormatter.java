package ru.javawebinar.topjava.web.util;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) {
        if (text.length() == 0) {
            return null;
        }
        return LocalDate.parse(text);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(getDateFormat());
    }

    protected DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ISO_DATE;
    }
}