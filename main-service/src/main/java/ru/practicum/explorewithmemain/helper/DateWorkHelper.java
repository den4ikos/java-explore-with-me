package ru.practicum.explorewithmemain.helper;

import ru.practicum.explorewithmemain.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DateWorkHelper {
    public static LocalDateTime makeDateFromRequest(String key, String value) {
        if (key.equals("start")) {
            return null == value
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constants.dateFormat));
        }

        if (key.equals("end")) {
            return null == value
                    ? LocalDateTime.now().plusYears(1)
                    : LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constants.dateFormat));
        }

        return LocalDateTime.now();
    }
}
