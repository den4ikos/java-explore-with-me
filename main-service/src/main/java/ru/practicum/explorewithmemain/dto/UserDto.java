package ru.practicum.explorewithmemain.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
