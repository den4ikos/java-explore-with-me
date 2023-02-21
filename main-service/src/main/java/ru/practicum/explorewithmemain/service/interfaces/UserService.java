package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.*;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto createUserRequest(Long userId, Long eventId);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long userId);

    List<UserDto> getUsers(Map<String, Object> params);

    List<EventShortDto> getUserEvents(Map<String, Object> params);

    EventFullDto createUserEvent(NewEventDto newEventDto, Long userId);
}
