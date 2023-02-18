package ru.practicum.explorewithmemain.dto;

import lombok.Data;

@Data
public class ViewStatisticDto {
    private String app;
    private String uri;
    private Long hits;
}
