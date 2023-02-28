package ru.practicum.explorewithmemain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.explorewithmemain.helper.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    @JsonProperty(value = "event")
    @NotNull(message = "Event is required")
    private Long eventId;
    @JsonProperty(value = "requester")
    @NotNull(message = "Requester is required")
    private Long requesterId;
    private Status status;
}
