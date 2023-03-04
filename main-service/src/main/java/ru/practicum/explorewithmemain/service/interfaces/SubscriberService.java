package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.SubscribeDto;

public interface SubscriberService {
    SubscribeDto sendRequest(Long userId, Long signatoryId, Long eventId);
}
