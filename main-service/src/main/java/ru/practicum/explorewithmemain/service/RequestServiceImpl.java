package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.helper.Status;
import ru.practicum.explorewithmemain.service.interfaces.RequestService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    @Override
    public List<Request> findRequestByEventIdAndStatus(Long eventId, Status status) {
        return null;
    }
}
