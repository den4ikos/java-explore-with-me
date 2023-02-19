package ru.practicum.explorewithmemain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithmemain.Constants;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class HitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = Constants.dateFormat)
    private LocalDateTime timestamp;
}
