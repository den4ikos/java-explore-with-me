package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.HitDtoInterface;
import ru.practicum.explorewithme.dto.StatisticDto;

public class StatisticMapper {
    public static StatisticDto toDto(HitDtoInterface hitDto) {
        return new StatisticDto(
                hitDto.getApp(), hitDto.getUri(), hitDto.getHits()
        );
    }
}
