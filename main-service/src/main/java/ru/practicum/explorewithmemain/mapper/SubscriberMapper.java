package ru.practicum.explorewithmemain.mapper;

import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Subscriber;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

import java.time.LocalDateTime;

public class SubscriberMapper {
    public static SubscribeDto toNewDto(User subscriber, User signatory, Event event) {
        return SubscribeDto
                .builder()
                .id(null)
                .subscriber(subscriber)
                .signatory(signatory)
                .event(event)
                .status(SubscriberStatus.PENDING)
                .build();
    }

    public static Subscriber toNewSubscriberModel(SubscribeDto subscribeDto) {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(subscribeDto.getId());
        subscriber.setSubscriber(subscribeDto.getSubscriber());
        subscriber.setEventOwner(subscribeDto.getSignatory());
        subscriber.setEvent(subscribeDto.getEvent());
        subscriber.setStatus(subscribeDto.getStatus());
        subscriber.setCreated(LocalDateTime.now());

        return subscriber;
    }

    public static SubscribeDto toDto(Subscriber subscriber) {
        return SubscribeDto
                .builder()
                .id(subscriber.getId())
                .subscriber(subscriber.getSubscriber())
                .signatory(subscriber.getEventOwner())
                .event(subscriber.getEvent())
                .status(subscriber.getStatus())
                .build();
    }
}
