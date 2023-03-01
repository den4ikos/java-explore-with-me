package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Subscriber;

public interface SubscribeRepository extends JpaRepository<Subscriber, Long> {
}
