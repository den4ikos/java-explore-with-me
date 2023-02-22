package ru.practicum.explorewithmemain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewCategoryDto {
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;
}
