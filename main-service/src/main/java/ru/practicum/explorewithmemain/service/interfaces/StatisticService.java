package ru.practicum.explorewithmemain.service.interfaces;

import java.util.Map;

public interface StatisticService {
    Long countViews(String uri);
    void addEventHits(Map<String, String> params);
}
