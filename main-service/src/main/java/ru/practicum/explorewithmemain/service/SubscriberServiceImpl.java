package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Subscriber;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;
import ru.practicum.explorewithmemain.helper.SubscriptionHelper;
import ru.practicum.explorewithmemain.mapper.EventMapper;
import ru.practicum.explorewithmemain.mapper.SubscriberMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.repository.SubscribeRepository;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SubscriberServiceImpl implements SubscriberService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SubscribeRepository subscribeRepository;
    private final EventMapper eventMapper;

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

        if (!SubscriptionHelper.getAllowedStates().contains(event.getState())) {
            throw new ConflictException(Constants.addSubscriptionToUnpublishedEventError);
        }

        SubscribeDto subscribeDto = SubscriberMapper.toNewDto(subscriber, signatory, event);

        Subscriber savedSubscriber = subscribeRepository.save(SubscriberMapper.toNewSubscriberModel(subscribeDto));

        return SubscriberMapper.toDto(savedSubscriber);
    }

    @Override
    @Transactional
    public SubscribeDto updateSubscriptionStatue(Long id, Long signatoryId, Long eventId, SubscriberStatus status) {
        Subscriber subscription = subscribeRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscription with id " + id)));

        if (!subscription.getSignatory().getId().equals(signatoryId)) {
            throw new ConflictException(Constants.accessibleConflict);
        }

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with id " + eventId)));

        if (!SubscriptionHelper.getAllowedStates().contains(event.getState())) {
            throw new ConflictException(Constants.eventSubscribeConflictStatus);
        }

        subscription.setStatus(status);

        Subscriber updatedSubscriber = subscribeRepository.save(subscription);

        return SubscriberMapper.toDto(updatedSubscriber);
    }

    @Override
    @Transactional
    public SubscribeDto rejectSubscription(Long subscriptionId, Long subscriberId) {
        Subscriber subscription = subscribeRepository
                .findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscription with id " + subscriptionId)));

        if (!subscription.getSubscriber().getId().equals(subscriberId)) {
            throw new ConflictException(Constants.accessibleConflict);
        }

        Long eventId = subscription.getEvent().getId();
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with id " + eventId)));

        if (!SubscriptionHelper.getAllowedStates().contains(event.getState())) {
            throw new ConflictException(Constants.eventSubscribeConflictStatus);
        }

        subscription.setStatus(SubscriberStatus.REJECTED);
        Subscriber updatedSubscription = subscribeRepository.save(subscription);

        return SubscriberMapper.toDto(updatedSubscription);
    }

    @Override
    @Transactional
    public List<SubscribeDto> getAllBySubscriber(Long subscriberId) {
        User subscriber = userRepository
                .findById(subscriberId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscriber with id " + subscriberId)));

        List<Subscriber> subscribers = subscribeRepository.findAllBySubscriberAndStatus(subscriber, SubscriberStatus.CONFIRMED);

        return subscribers
                .stream()
                .map(SubscriberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SubscribeDto> getAllBySignatory(Long signatoryId) {
        User signatory = userRepository
                .findById(signatoryId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Signatory with id " + signatoryId)));

        List<Subscriber> signatories = subscribeRepository.findAllBySignatoryAndStatus(signatory, SubscriberStatus.CONFIRMED);

        return signatories
                .stream()
                .map(SubscriberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EventFullDto> getSubscriberEvents(Long subscriberId) {
        User subscriber = userRepository
                .findById(subscriberId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Subscriber with id " + subscriberId)));

        Set<Event> events = subscribeRepository.findSubscriberEventIds(subscriber);

        return events
                .stream()
                .map(eventMapper::toFullDto)
                .collect(Collectors.toList());
    }
}
