package ru.practicum.explorewithmemain.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.*;
import ru.practicum.explorewithmemain.entity.Category;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.helper.Status;
import ru.practicum.explorewithmemain.service.interfaces.RequestService;
import ru.practicum.explorewithmemain.service.interfaces.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class EventMapper {
    private final RequestService requestService;
    private final StatisticService statisticService;

    public EventShortDto toShortDto(Event event) {
        Long confirmRequestsCount = getConfirmedRequest(event);

        Long viewsCount = statisticService.countViews("/events/" + event.getId());
        Category cat = event.getCategory();
        User initiator = event.getInitiator();

        return EventShortDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .categoryDto(new CategoryDto(cat.getId(), cat.getName()))
                .confirmedRequest(confirmRequestsCount)
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(initiator.getId(), initiator.getName()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .hits(viewsCount)
                .build();
    }

    public EventFullDto toFullDto(Event event) {
        Long confirmRequestsCount = getConfirmedRequest(event);

        Long viewsCount = statisticService.countViews("/events/" + event.getId());

        Category cat = event.getCategory();

        User initiator = event.getInitiator();

        return EventFullDto
                .builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .categoryDto(new CategoryDto(cat.getId(), cat.getName()))
                .confirmedRequests(confirmRequestsCount)
                .createdOn(event.getCreatedAt())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(initiator.getId(), initiator.getName()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .views(viewsCount)
                .participantLimit(event.getParticipantLimit())
                .build();
    }

    private Long getConfirmedRequest(Event event) {
        List<Request> r = requestService.findRequestByEventIdAndStatus(event.getId(), Status.CONFIRMED);
        return (long) r.size();
    }

    public EventCreateDto toCreateDto(NewEventDto newEventDto, UserDto initiator, CategoryDto categoryDto) {
        if (null == newEventDto) return null;

        return EventCreateDto
                .builder()
                .annotation(newEventDto.getAnnotation())
                .categoryDto(categoryDto)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(initiator)
                .paid(newEventDto.getPaid())
                .location(newEventDto.getLocation())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .createdOn(LocalDateTime.now())
                .build();
    }

    public Event eventCreateDtoToEvent(EventCreateDto eventCreateDto) {
        if (null == eventCreateDto) return null;

        CategoryDto categoryDto = eventCreateDto.getCategoryDto();
        UserDto initiator = eventCreateDto.getInitiator();

        return Event
                .builder()
                .id(null)
                .title(eventCreateDto.getTitle())
                .annotation(eventCreateDto.getAnnotation())
                .description(eventCreateDto.getDescription())
                .category(new Category(categoryDto.getId(), categoryDto.getName()))
                .eventDate(eventCreateDto.getEventDate())
                .initiator(new User(initiator.getId(), initiator.getEmail(), initiator.getName()))
                .location(eventCreateDto.getLocation())
                .paid(eventCreateDto.getPaid())
                .participantLimit(eventCreateDto.getParticipantLimit())
                .requestModeration(eventCreateDto.getRequestModeration())
                .state(State.PENDING)
                .build();
    }

    public Event fromFullDtoToEvent(Event event, UpdateAdminEventDto eventFullDto) {
        if (null != eventFullDto.getAnnotation()) {
            event.setAnnotation(eventFullDto.getAnnotation());
        }
        if (null != eventFullDto.getAnnotation()) {
            event.setDescription(eventFullDto.getDescription());
        }

        if (null != eventFullDto.getEventDate()) {
            event.setEventDate(eventFullDto.getEventDate());
        }

        if (null != eventFullDto.getLocation()) {
            event.setLocation(eventFullDto.getLocation());
        }

        if (null != eventFullDto.getPaid()) {
            event.setPaid(eventFullDto.getPaid());
        }

        if (null != eventFullDto.getParticipantLimit()) {
            event.setParticipantLimit(eventFullDto.getParticipantLimit());
        }

        if (null != eventFullDto.getRequestModeration()) {
            event.setRequestModeration(eventFullDto.getRequestModeration());
        }

        if (null != eventFullDto.getStateAction()) {
            event.setState(eventFullDto.getStateAction());
        }

        if (null != eventFullDto.getTitle()) {
            event.setTitle(eventFullDto.getTitle());
        }

        return event;
    }
}
