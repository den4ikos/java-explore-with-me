package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatisticDto;

public class StatisticMapper {
    public static StatisticDto toDto(HitDto hitDto) {
        return new StatisticDto(
                hitDto.getApp(), hitDto.getUri(), hitDto.getId()
        );
    }
}
