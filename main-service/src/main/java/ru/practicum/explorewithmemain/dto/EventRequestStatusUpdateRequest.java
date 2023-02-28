package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithmemain.helper.Status;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private Status status;
}
