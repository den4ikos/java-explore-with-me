package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.helper.Status;

import java.util.List;

public interface RequestService {
    List<Request> findRequestByEventIdAndStatus(Long eventId, Status status);
}
