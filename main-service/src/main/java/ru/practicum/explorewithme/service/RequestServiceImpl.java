package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.entity.Request;
import ru.practicum.explorewithme.helper.Status;
import ru.practicum.explorewithme.service.interfaces.RequestService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Override
    public List<Request> findRequestByEventIdAndStatus(Long eventId, Status status) {
        return null;
    }
}
