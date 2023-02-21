package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.mapper.EventMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.service.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventFullDto> get(Map<String, Object> params) {
        LocalDateTime rangeStart = (LocalDateTime) params.get("start");
        LocalDateTime rangeEnd = (LocalDateTime) params.get("end");

        if (rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("The end date of the event can't be earlier than the event's start date.");
        }

        Set<State> states;

        if (!params.containsKey("states")) {
            states = new HashSet<>();
            states.add(State.PUBLISHED);
            states.add(State.CANCELED);
            states.add(State.PENDING);
        } else {
            states = (Set<State>) params.get("states");
        }

        List<Event> events = new ArrayList<>();

        int from = params.containsKey("from")
                ? (int) params.get("from")
                : 0;

        int size = params.containsKey("size")
                ? (int) params.get("size")
                : 0;

        Pageable page = PageRequest.of( (from/size), size );

        if ((!params.containsKey("categories") || null == params.get("categories")) && (!params.containsKey("users") || null == params.get("users"))) {
            events = eventRepository.findEventsByStates(states, page);
        }

        if ( params.containsKey("categories") && null != params.get("categories") && params.containsKey("users") && null != params.get("users") ) {
            Set<Long> categories = (Set<Long>) params.get("categories");
            Set<Long> users = (Set<Long>) params.get("users");
            events = eventRepository.findEventsByCategoriesAndUsersAndStates(
                    categories,
                    users,
                    states,
                    page
            );
        }

        if ( params.containsKey("categories") && null != params.get("categories") && ( !params.containsKey("users") || null == params.get("users") ) ) {
            Set<Long> categories = (Set<Long>) params.get("categories");
            events = eventRepository.findEventsByCategoriesAndStates(categories, states, page);
        }

        if ( (!params.containsKey("categories") || null == params.get("categories")) && params.containsKey("users") && null != params.get("users") ) {
            Set<Long> users = (Set<Long>) params.get("users");
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
}
