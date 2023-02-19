package ru.practicum.explorewithmemain.service;

import org.springframework.stereotype.Service;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<EventShortDto> get(Map<String, Object> params) {
        LocalDateTime rangeStart = (LocalDateTime) params.get("start");
        LocalDateTime rangeEnd = (LocalDateTime) params.get("end");

        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("The end date of the event can't be earlier than the event's start date.");
        }

        return null;
    }
}
