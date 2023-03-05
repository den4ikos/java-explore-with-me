package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

import java.util.List;
import java.util.Map;

public interface SubscriberService {
    SubscribeDto getSubscribeById(Long id);

    SubscribeDto getSubscriber(Long subscriberId);

    SubscribeDto sendRequest(Long userId, Long signatoryId, Long eventId);

    SubscribeDto getSubscribeBySignatoryId(Long signatoryId);

    SubscribeDto getSubscribeByEventId(Long eventId);

    SubscribeDto updateSubscriptionStatue(Long id, Long signatoryId, Long eventID, SubscriberStatus status);

    SubscribeDto rejectSubscription(Long subscriptionId, Long subscriberId);

    List<SubscribeDto> getAllBySubscriber(Long subscriberId);

    List<SubscribeDto> getAllBySignatory(Long signatoryId);

    List<EventFullDto> getSubscriberEvents(Long subscriberId);

    Map<String, String> deleteSubscription(Long subscriptionId);

    Map<String, String> deleteAllBySubscriber(Long subscriberId);
}
