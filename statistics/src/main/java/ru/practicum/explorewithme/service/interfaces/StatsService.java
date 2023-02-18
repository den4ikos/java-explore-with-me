package ru.practicum.explorewithme.service.interfaces;

import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatisticDto;

import java.util.List;

public interface StatsService {
    HitDto create(HitDto hitDto);
    List<StatisticDto> get(String start, String end, List<String> uris, Boolean unique);
}
