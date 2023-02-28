package ru.practicum.explorewithmemain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventShortDto {
    private Long id;
    private String annotation;
    @JsonProperty("category")
    private CategoryDto categoryDto;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
