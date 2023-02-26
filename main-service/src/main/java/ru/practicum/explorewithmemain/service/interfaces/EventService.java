package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.dto.UpdateAdminEventDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.helper.State;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EventService {

    List<EventShortDto> getShort(Map<String, Object> params);

    Event getEventById(Long id);

    EventFullDto update(Long eventId, UpdateAdminEventDto eventDto);

    List<EventFullDto> get(Set<Long> users, Set<State> states, Set<Long> categories, LocalDateTime start, LocalDateTime end, int from, int size);

    EventFullDto getFull(Long id, HttpServletRequest request);
}
