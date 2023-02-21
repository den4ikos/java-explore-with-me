package ru.practicum.explorewithmemain.dto;

import lombok.*;
import ru.practicum.explorewithmemain.entity.Location;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventCreateDto {
    private String annotation;
    private CategoryDto categoryDto;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserDto initiator;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    private Long confirmedRequest;
}
