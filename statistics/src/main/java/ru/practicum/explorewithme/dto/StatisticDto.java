package ru.practicum.explorewithme.dto;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
public class StatisticDto {
    private String app;
    private String uri;
    private Long hit;
}
