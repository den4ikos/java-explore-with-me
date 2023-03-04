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
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.mapper.SubscriberMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.repository.SubscribeRepository;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

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

        SubscribeDto subscribeDto = SubscriberMapper.toNewDto(subscriber, signatory, event);

        Subscriber savedSubscriber = subscribeRepository.save(SubscriberMapper.toNewSubscriberModel(subscribeDto));

        return SubscriberMapper.toDto(savedSubscriber);
    }
}
