package ru.practicum.explorewithmemain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithmemain.entity.Location;
import ru.practicum.explorewithmemain.helper.EventNewState;
import ru.practicum.explorewithmemain.helper.State;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateAdminEventDto {
    @Size(min = 20, max = 2000)
    private String annotation;
    @JsonProperty("category")
    private Long categoryId;
    @Size(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
