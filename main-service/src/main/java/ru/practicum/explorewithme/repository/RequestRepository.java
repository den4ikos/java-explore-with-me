package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.entity.Request;
import ru.practicum.explorewithme.helper.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventIdAndStatus(Long eventId, Status status);
}
