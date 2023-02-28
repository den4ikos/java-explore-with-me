package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NewCompilationDto {
    @NotBlank(message = "Title must not be blank")
    private String title;
    private Boolean pinned;
    private List<Long> events;

    public NewCompilationDto() {
        this.pinned = false;
    }
}
