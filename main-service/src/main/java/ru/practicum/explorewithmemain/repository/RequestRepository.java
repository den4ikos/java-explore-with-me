package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEventIdAndStatus(Long eventId, Status status);

    List<Request> findAllByRequestorId(Long requestorId);

    @Query("select r from Request r left join Event e on r.event = e where e = :event and e.initiator = :initiator")
    List<Request> getRequestsByEventAndEventInitiator(@Param("event") Event event, @Param("initiator") User initiator);
}
