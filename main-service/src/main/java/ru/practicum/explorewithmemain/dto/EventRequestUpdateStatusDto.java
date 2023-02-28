package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithmemain.helper.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EventRequestUpdateStatusDto {
    private List<Long> requestIds;
    @Enumerated(EnumType.STRING)
    private Status status;
}
