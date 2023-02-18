package ru.practicum.explorewithme.dto;

import lombok.Data;

@Data
public class ViewStatisticDto {
    private String app;
    private String uri;
    private Long hits;
}
