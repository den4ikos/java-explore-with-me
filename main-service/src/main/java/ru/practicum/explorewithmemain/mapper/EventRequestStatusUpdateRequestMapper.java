package ru.practicum.explorewithmemain.mapper;

import ru.practicum.explorewithmemain.dto.ConfirmedRequestDto;
import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;
import ru.practicum.explorewithmemain.dto.RejectedRequestDto;

public class EventRequestStatusUpdateRequestMapper {
    public static ConfirmedRequestDto confirmedRequestDto(ParticipationRequestDto participationRequestDto) {
        return ConfirmedRequestDto
                .builder()
                .created(null != participationRequestDto.getCreated() ? participationRequestDto.getCreated() : null)
                .event(null != participationRequestDto.getEventId() ? participationRequestDto.getEventId() : null)
                .id(null != participationRequestDto.getId() ? participationRequestDto.getId() : null)
                .requester(null != participationRequestDto.getRequesterId() ? participationRequestDto.getRequesterId() : null)
                .status(null != participationRequestDto.getStatus() ? participationRequestDto.getStatus() : null)
                .build();
    }

    public static RejectedRequestDto rejectedRequestDto(ParticipationRequestDto participationRequestDto) {
        return RejectedRequestDto
                .builder()
                .created(null != participationRequestDto.getCreated() ? participationRequestDto.getCreated() : null)
                .event(null != participationRequestDto.getEventId() ? participationRequestDto.getEventId() : null)
                .id(null != participationRequestDto.getId() ? participationRequestDto.getId() : null)
                .requester(null != participationRequestDto.getRequesterId() ? participationRequestDto.getRequesterId() : null)
                .status(null != participationRequestDto.getStatus() ? participationRequestDto.getStatus() : null)
                .build();
    }
}
