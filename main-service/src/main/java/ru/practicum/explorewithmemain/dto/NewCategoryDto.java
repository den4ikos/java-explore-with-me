package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NewCategoryDto {
    @NotBlank(message = "Name is required")
    private String name;
}
