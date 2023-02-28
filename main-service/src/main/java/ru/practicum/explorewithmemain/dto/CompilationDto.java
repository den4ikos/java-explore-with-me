package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private Set<EventShortDto> events;
}
