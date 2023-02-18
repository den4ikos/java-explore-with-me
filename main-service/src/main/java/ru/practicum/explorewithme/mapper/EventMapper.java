package ru.practicum.explorewithme.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.UserShortDto;
import ru.practicum.explorewithme.entity.Category;
import ru.practicum.explorewithme.entity.Event;
import ru.practicum.explorewithme.entity.Request;
import ru.practicum.explorewithme.entity.User;
import ru.practicum.explorewithme.helper.Status;
import ru.practicum.explorewithme.service.interfaces.RequestService;
import ru.practicum.explorewithme.service.interfaces.StatisticService;

import java.util.List;

@Component
@AllArgsConstructor
public class EventMapper {
    private final RequestService requestService;
    private final StatisticService statisticService;

    public EventShortDto toShortDto(Event event) {
        List<Request> confirmedRequests = requestService.findRequestByEventIdAndStatus(event.getId(), Status.CONFIRMED);
        Long confirmRequestsCount = (long) confirmedRequests.size();

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
}
