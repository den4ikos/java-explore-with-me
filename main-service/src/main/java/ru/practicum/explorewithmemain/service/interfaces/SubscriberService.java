package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

public interface SubscriberService {
    SubscribeDto getSubscribeById(Long id);

    SubscribeDto getSubscriber(Long subscriberId);

    SubscribeDto sendRequest(Long userId, Long signatoryId, Long eventId);

    SubscribeDto getSubscribeBySignatoryId(Long signatoryId);

    SubscribeDto getSubscribeByEventId(Long eventId);

    SubscribeDto updateSubscriptionStatue(Long id, Long signatoryId, Long eventID, SubscriberStatus status);
}
