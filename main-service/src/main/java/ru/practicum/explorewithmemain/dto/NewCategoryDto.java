package ru.practicum.explorewithmemain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewCategoryDto {
    @NotBlank(message = "Name is required")
    private String name;
}
