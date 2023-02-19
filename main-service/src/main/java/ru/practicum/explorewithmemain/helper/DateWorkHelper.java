package ru.practicum.explorewithmemain.helper;

import ru.practicum.explorewithmemain.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DateWorkHelper {
    public static LocalDateTime makeDateFromRequest(Map<String, String> params) {
        if (params.containsKey("start")) {
            String start = params.get("start");
            return null == start
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(start, DateTimeFormatter.ofPattern(Constants.dateFormat));
        }

        if (params.containsKey("end")) {
            String period = params.getOrDefault("plusPeriod", "1");
            String end = params.get("end");
            return null == end
                    ? LocalDateTime.now().plusYears(Integer.parseInt(period))
                    : LocalDateTime.parse(end, DateTimeFormatter.ofPattern(Constants.dateFormat));
        }

        return LocalDateTime.now();
    }
}
