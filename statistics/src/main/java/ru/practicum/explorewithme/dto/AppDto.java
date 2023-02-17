package ru.practicum.explorewithme.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppDto {
    private Long id;

    @NotNull(message = "Name is required")
    @Max(value = 100, message = "Max value must be less than or equal to 100 characters")
    private String name;
}
