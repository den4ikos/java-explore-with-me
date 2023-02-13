package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.annotation.IpCheckConstraint;
import ru.practicum.explorewithme.annotation.LocalDateTimeConstraint;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitDto {
    private Long id;
    private String app;
    private String uri;

    @IpCheckConstraint
    private String ip;

    @LocalDateTimeConstraint
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime timestamp;
}
