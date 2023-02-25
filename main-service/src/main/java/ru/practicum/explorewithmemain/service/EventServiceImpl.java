package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.dto.UpdateAdminEventDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.mapper.EventMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getShort(Map<String, Object> params) {
        LocalDateTime rangeStart = (LocalDateTime) params.get("start");
        LocalDateTime rangeEnd = (LocalDateTime) params.get("end");

        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException(Constants.eventDateError);
        }

        List<Event> events;

        if (params.containsKey("categories") && null != params.get("categories")) {
            Set<Long> categories = (Set<Long>) params.get("categories");
            events = eventRepository.findEventsForPublicWithCategoriesAndText(categories, (String) params.get("text"));
        } else {
            events = eventRepository.findEventsForPublicByText((String) params.get("text"));
        }

        return events
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> get(
            Set<Long> users,
            Set<State> states,
            Set<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            int from,
            int size) {
        System.out.println(users);
        System.out.println(states);
        System.out.println(categories);
        System.out.println(rangeStart);
        System.out.println(rangeEnd);
        System.out.println(from);
        System.out.println(size);

        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException(Constants.eventDateError);
        }

        if (null != states) {
            states = new HashSet<>();
            states.add(State.PUBLISHED);
            states.add(State.CANCELED);
            states.add(State.PENDING);
        }

        List<Event> events = new ArrayList<>();

        Pageable page = PageRequest.of( (from/size), size );

        if ( null == categories && null == users) {
            events = eventRepository.findEventsByStates(states, page);
        }

        if ( null != categories &&  null != users ) {
            events = eventRepository.findEventsByCategoriesAndUsersAndStates(
                    categories,
                    users,
                    states,
                    page
            );
        }

        if ( null != categories && null == users ) {
            events = eventRepository.findEventsByCategoriesAndStates(categories, states, page);
        }

        if ( null == categories && null != users ) {
            events = eventRepository.findEventsByEventsAndStates(users, states, page);
        }

        return events
                .stream()
                .map(eventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event")));
    }

    @Override
    public EventFullDto update(Long eventId, UpdateAdminEventDto eventDto) {
        Event currentEvent = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event " + eventId)));

        if (currentEvent.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format(Constants.eventConflictStatus, currentEvent.getState()));
        }

        return null;
    }
}
