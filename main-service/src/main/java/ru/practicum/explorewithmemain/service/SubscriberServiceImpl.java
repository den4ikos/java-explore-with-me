package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Subscriber;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.mapper.SubscriberMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.repository.SubscribeRepository;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SubscriberServiceImpl implements SubscriberService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SubscribeRepository subscribeRepository;

    @Override
    @Transactional
    public SubscribeDto getSubscribeById(Long id) {
        Subscriber subscribe = subscribeRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscription with ID " + id)));

        return SubscriberMapper.toDto(subscribe);
    }

    @Override
    @Transactional
    public SubscribeDto getSubscriber(Long subscriberId) {
        User subscriber = userRepository
                .findById(subscriberId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscription with subscriberId " + subscriberId)));

        Subscriber subscribe = subscribeRepository
                .findBySubscriber(subscriber);

        if (null == subscribe) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Subscribe for subscriber " + subscriberId));
        }

        System.out.println("\n\n\n\n\nAZZAZ " + subscribe);

        return SubscriberMapper.toDto(subscribe);
    }

    @Override
    @Transactional
    public SubscribeDto getSubscribeBySignatoryId(Long signatoryId) {
        User signatory = userRepository
                .findById(signatoryId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Signatory with id " + signatoryId)));

        Subscriber subscriber = subscribeRepository.findBySignatory(signatory);

        if (null == subscriber) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Subscription"));
        }

        return SubscriberMapper.toDto(subscriber);
    }

    @Override
    @Transactional
    public SubscribeDto getSubscribeByEventId(Long eventId) {
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with id " + eventId)));

        Subscriber subscriber = subscribeRepository.findByEvent(event);

        if (null == subscriber) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Subscription"));
        }

        return SubscriberMapper.toDto(subscriber);
    }

    @Override
    @Transactional
    public SubscribeDto sendRequest(Long userId, Long signatoryId, Long eventId) {
        User subscriber = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscriber with ID " + userId)));

        User signatory = userRepository
                .findById(signatoryId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Signatory with ID " + signatoryId)));

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with ID " + eventId)));

        State[] statesArray = new State[]{State.PUBLISHED, State.PUBLISH_EVENT};
        List<State> allowedStates = Arrays.asList(statesArray);

        if (!allowedStates.contains(event.getState())) {
            throw new ConflictException(Constants.addSubscriptionToUnpublishedEventError);
        }

        SubscribeDto subscribeDto = SubscriberMapper.toNewDto(subscriber, signatory, event);

        Subscriber savedSubscriber = subscribeRepository.save(SubscriberMapper.toNewSubscriberModel(subscribeDto));

        return SubscriberMapper.toDto(savedSubscriber);
    }
}
