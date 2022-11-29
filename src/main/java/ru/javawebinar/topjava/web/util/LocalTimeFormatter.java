package ru.javawebinar.topjava.web.util;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) {
        if (text.length() == 0) {
            return null;
        }
        return LocalTime.parse(text);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.format(getTimeFormat());
    }

    protected DateTimeFormatter getTimeFormat() {
        return DateTimeFormatter.ISO_TIME;
    }
}