package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
