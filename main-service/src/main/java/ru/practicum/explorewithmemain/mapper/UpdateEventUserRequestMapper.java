package ru.practicum.explorewithmemain.mapper;

import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.UpdateEventUserRequestDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.helper.State;

import java.time.LocalDateTime;

public class UpdateEventUserRequestMapper {
    public static Event toEvent(Event event, UpdateEventUserRequestDto updateEventUserRequestDto) {
        if (null != updateEventUserRequestDto.getAnnotation()) {
            event.setAnnotation(updateEventUserRequestDto.getAnnotation());
        }

        if (null != updateEventUserRequestDto.getDescription()) {
            event.setDescription(updateEventUserRequestDto.getDescription());
        }

        if (null != updateEventUserRequestDto.getEventDate()) {
            if (updateEventUserRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException(Constants.dateScheduleConflict);
            } else {
                event.setEventDate(updateEventUserRequestDto.getEventDate());
            }
        }

        if (null != updateEventUserRequestDto.getLocation()) {
            event.setLocation(updateEventUserRequestDto.getLocation());
        }

        if (null != updateEventUserRequestDto.getPaid()) {
            event.setPaid(updateEventUserRequestDto.getPaid());
        }

        if (null != updateEventUserRequestDto.getParticipantLimit()) {
            event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit());
        }

        if (null != updateEventUserRequestDto.getRequestModeration()) {
            event.setRequestModeration(updateEventUserRequestDto.getRequestModeration());
        }

        if (null != updateEventUserRequestDto.getTitle()) {
            event.setTitle(updateEventUserRequestDto.getTitle());
        }

        if (State.PENDING.equals(event.getState()) || State.REJECT_EVENT.equals(event.getState())) {
            event.setState(State.PENDING);
        } else {
            throw new ConflictException(Constants.eventCanceledConflict);
        }

        return event;
    }
}
