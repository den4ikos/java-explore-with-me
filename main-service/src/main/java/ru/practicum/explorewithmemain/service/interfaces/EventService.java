package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.EventShortDto;

import java.util.List;
import java.util.Map;

public interface EventService {
    List<EventShortDto> get(Map<String, Object> params);
}
