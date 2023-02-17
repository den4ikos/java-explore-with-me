package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.AppDto;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.entity.App;
import ru.practicum.explorewithme.entity.Hit;

public class HitMapper {

    public static Hit toHit(HitDto hitDto, AppDto appDto) {
        Hit hit = new Hit();
        hit.setAppId(appDto.getId());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }

    public static HitDto toDto(Hit hit, App app) {
        return new HitDto(
                hit.getId(),
                app.getName(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}
