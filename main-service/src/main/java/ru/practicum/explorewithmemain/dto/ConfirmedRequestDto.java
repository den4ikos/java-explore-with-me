package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithmemain.helper.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ConfirmedRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    @Enumerated(EnumType.STRING)
    private Status status;
}
