package ru.practicum.explorewithmemain.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
}
