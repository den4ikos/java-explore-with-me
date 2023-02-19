package ru.practicum.explorewithmemain.mapper;

import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.Status;

import java.time.LocalDateTime;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto
                .builder()
                .id(request.getId())
                .created(request.getCreated())
                .eventId(request.getEvent().getId())
                .requesterId(request.getRequestor().getId())
                .status(request.getStatus())
                .build();

    }

    public static Request toParticipationRequest(User user, Event event, Status status) {
        return new Request(
                null,
                event,
                user,
                status,
                LocalDateTime.now()
        );
    }
}
