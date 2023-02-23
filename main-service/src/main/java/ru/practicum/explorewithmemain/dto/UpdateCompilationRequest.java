package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdateCompilationRequest {
    @NotBlank(message = "Title must not be blank")
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
