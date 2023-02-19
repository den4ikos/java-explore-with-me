package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select exists(select 1 from events where id = :id)", nativeQuery = true)
    Boolean isEventExists(@Param("id") Long eventId);
}
