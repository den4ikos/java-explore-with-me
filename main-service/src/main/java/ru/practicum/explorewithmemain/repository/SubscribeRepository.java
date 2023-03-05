package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Subscriber;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

import java.util.List;
import java.util.Set;

public interface SubscribeRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findBySubscriber(User subscriber);

    Subscriber findBySignatory(User signatory);

    Subscriber findByEvent(Event event);

    List<Subscriber> findAllBySignatoryAndStatus(User signatory, SubscriberStatus status);

    @Query(value = "select s.event from Subscriber s where s.subscriber = :subscriber and s.status = 'CONFIRMED'")
    Set<Event> findSubscriberEventIds(@Param("subscriber") User subscriber);

    List<Subscriber> findAllBySubscriberAndStatus(User subscriber, SubscriberStatus status);
}
