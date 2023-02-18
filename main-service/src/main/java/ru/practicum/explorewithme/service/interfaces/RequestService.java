package ru.practicum.explorewithme.service.interfaces;

import ru.practicum.explorewithme.entity.Request;
import ru.practicum.explorewithme.helper.Status;

import java.util.List;

public interface RequestService {
    List<Request> findRequestByEventIdAndStatus(Long eventId, Status status);
}
