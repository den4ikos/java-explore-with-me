package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.helper.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEventIdAndStatus(Long eventId, Status status);

    List<Request> findAllByRequestorId(Long requestorId);
}
