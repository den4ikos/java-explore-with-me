package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.SubscribeDto;

public interface SubscriberService {
    SubscribeDto getSubscribeById(Long id);

    SubscribeDto getSubscriber(Long subscriberId);

    SubscribeDto sendRequest(Long userId, Long signatoryId, Long eventId);

    SubscribeDto getSubscribeBySignatoryId(Long signatoryId);

    SubscribeDto getSubscribeByEventId(Long eventId);
}
