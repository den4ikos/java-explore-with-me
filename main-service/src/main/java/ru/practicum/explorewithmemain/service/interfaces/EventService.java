package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.dto.UpdateAdminEventDto;
import ru.practicum.explorewithmemain.entity.Event;

import java.util.List;
import java.util.Map;

public interface EventService {
    List<EventFullDto> get(Map<String, Object> params);

    List<EventShortDto> getShort(Map<String, Object> params);

    Event getEventById(Long id);

    EventFullDto update(Long eventId, UpdateAdminEventDto eventDto);
}
