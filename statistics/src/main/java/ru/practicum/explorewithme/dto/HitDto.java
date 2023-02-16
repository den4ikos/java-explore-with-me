package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.Constants;
import ru.practicum.explorewithme.annotation.IpCheckConstraint;

import javax.validation.constraints.Max;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitDto {
    private Long id;

    @Max(value = 100, message = "Max value must be less than or equal to 100 characters")
    private String app;

    @Max(value = 100, message = "Max value must be less than or equal to 100 characters")
    private String uri;

    @IpCheckConstraint
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.dateFormat)
    private LocalDateTime timestamp;
}
