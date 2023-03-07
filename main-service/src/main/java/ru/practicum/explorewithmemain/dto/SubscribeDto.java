package ru.practicum.explorewithmemain.dto;

import lombok.*;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class SubscribeDto {
    private Long id;
    private User subscriber;
    private User signatory;
    private Event event;
    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;
}
