package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Hit {
    public Long id;
    public String app;
    public String uri;
    public String ip;
    public LocalDateTime timestamp;
}
