package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;
import ru.practicum.explorewithmemain.dto.UserDto;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.exception.AlreadyExistsException;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.helper.Status;
import ru.practicum.explorewithmemain.mapper.RequestMapper;
import ru.practicum.explorewithmemain.mapper.UserMapper;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.repository.RequestRepository;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return requestRepository
                .findAllByRequestorId(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createUserRequest(Long userId, Long eventId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.userNotFound, userId)));

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.eventNotFound, eventId)));

        if (event.getInitiator().getId().equals(userId)) {
            log.error(Constants.initiatorConflictMessage);
            throw new ConflictException(Constants.initiatorConflictMessage);
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            log.error(Constants.eventConflictMessage);
            throw new ConflictException(Constants.eventConflictMessage);
        }

        Request request = requestRepository.save(RequestMapper.toParticipationRequest(user, event, Status.PENDING));

        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        try {
            User u = userRepository.save(UserMapper.toUser(userDto));
            return UserMapper.toUserDto(u);
        } catch (DataIntegrityViolationException e) {
            log.error(String.format(Constants.emailAlreadyExists, userDto.getEmail()));
            throw new AlreadyExistsException(String.format(Constants.emailAlreadyExists, userDto.getEmail()));
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            log.error(String.format(Constants.notFoundError, "User"));
            throw new NotFoundException(String.format(Constants.notFoundError, "User"));
        }
    }

    @Override
    public List<UserDto> getUsers(Map<String, Object> params) {
        Long[] ids = (Long[]) params.get("ids");
        int from = (int) params.get("from");
        int size = (int) params.get("size");
        Pageable page = PageRequest.of( (from/size), size );
        List<User> users;
        if (null == ids) {
            users = userRepository.findAll(page).stream().collect(Collectors.toList());
        } else {
            users = userRepository.findByIdIsIn(Arrays.asList(ids), page);
        }

        return users
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
