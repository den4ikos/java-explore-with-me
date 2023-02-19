package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;

import java.util.List;

public interface UserService {

    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto createUserRequest(Long userId, Long eventId);
}
