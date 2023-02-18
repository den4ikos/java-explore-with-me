package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
}
