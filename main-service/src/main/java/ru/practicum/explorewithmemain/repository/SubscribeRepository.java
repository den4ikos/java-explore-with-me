package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Subscriber;
import ru.practicum.explorewithmemain.entity.User;

public interface SubscribeRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findBySubscriber(User subscriber);

    Subscriber findBySignatory(User signatory);

    Subscriber findByEvent(Event event);
}
