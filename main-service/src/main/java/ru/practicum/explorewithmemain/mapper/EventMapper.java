package ru.practicum.explorewithmemain.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.dto.UserShortDto;
import ru.practicum.explorewithmemain.entity.Category;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.Status;
import ru.practicum.explorewithmemain.service.interfaces.RequestService;
import ru.practicum.explorewithmemain.service.interfaces.StatisticService;

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
                .build();
    }

    private Long getConfirmedRequest(Event event) {
        List<Request> r = requestService.findRequestByEventIdAndStatus(event.getId(), Status.CONFIRMED);
        return (long) r.size();
    }
}
