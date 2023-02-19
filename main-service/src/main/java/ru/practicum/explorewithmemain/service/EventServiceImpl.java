package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> get(Map<String, Object> params) {
        LocalDateTime rangeStart = (LocalDateTime) params.get("start");
        LocalDateTime rangeEnd = (LocalDateTime) params.get("end");

        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("The end date of the event can't be earlier than the event's start date.");
        }

        return null;
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event")));
    }
}
