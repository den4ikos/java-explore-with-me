package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.*;
import ru.practicum.explorewithmemain.entity.Category;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.Request;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.Helper;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.helper.Status;
import ru.practicum.explorewithmemain.mapper.*;
import ru.practicum.explorewithmemain.repository.CategoryRepository;
import ru.practicum.explorewithmemain.repository.EventRepository;
import ru.practicum.explorewithmemain.repository.RequestRepository;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

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
        int requests = requestRepository.countByEventId(event.getId());

        if (event.getParticipantLimit() >= requests) {
            throw new ConflictException(Constants.membershipLimitConflict);
        }

        if (requestRepository.existsByRequestorIdAndEventId(user.getId(), event.getId())) {
            throw new ConflictException(String.format(Constants.alreadyExists, "Request", "user: " + user.getId() + " and event: " + event.getId()));
        }


        if (event.getInitiator().getId().equals(userId)) {
            log.error(Constants.initiatorConflictMessage);
            throw new ConflictException(Constants.initiatorConflictMessage);
        }

        if (event.getState().equals(State.PENDING)) {
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
            log.error("ERROR " + String.format(Constants.emailAlreadyExists, userDto.getEmail()));
            throw new ConflictException(String.format(Constants.emailAlreadyExists, userDto.getEmail()));
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
        int from = params.containsKey("from") && null != params.get("from") ? (int) params.get("from") : 0;
        int size = params.containsKey("size") && null != params.get("size") ? (int) params.get("size") : 10;
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

    @Override
    @Transactional
    public List<EventShortDto> getUserEvents(Map<String, Object> params) {
        if (!params.containsKey("userId")) throw new BadRequestException(Constants.badRequest);

        Long userId = (Long) params.get("userId");
        int from = params.containsKey("from") && null != params.get("from") ? (int) params.get("from") : 0;
        int size = params.containsKey("size") && null != params.get("size") ? (int) params.get("size") : 10;

        if (!userRepository.isUserExists(userId)) {
            throw new NotFoundException(String.format(Constants.userNotFound, userId));
        }

        Pageable page = PageRequest.of( (from/size), size );

        return eventRepository
                .findByInitiatorId(userId, page)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createUserEvent(NewEventDto newEventDto, Long userId) {
        User u = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.userNotFound, userId)));

        UserDto initiator = UserMapper.toUserDto(u);

        Category c = categoryRepository
                .findById(newEventDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Category")));

        CategoryDto categoryDto = categoryMapper.toDto(c);

        EventCreateDto ecd = eventMapper.toCreateDto(newEventDto, initiator, categoryDto);

        if (ecd.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException(Constants.eventDateError);
        }

        Event event = eventRepository.save(eventMapper.eventCreateDtoToEvent(ecd));
        return eventMapper.toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        User u = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.userNotFound, userId)));

        Event e = eventRepository
                    .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with id " + eventId)));

        Event savedEvent;

        if (!u.getId().equals(e.getInitiator().getId())) {
            throw new ConflictException(Constants.initiatorCanceled);
        }

        if (State.PENDING.equals(e.getState()) || State.REJECT_EVENT.equals(e.getState())) {
            e.setState(State.PENDING);
            savedEvent = eventRepository.save(e);
        } else {
            throw new ConflictException(Constants.eventCanceledConflict);
        }

        return eventMapper.toFullDto(savedEvent);
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> getEventRequestStatusUpdatedResult(
            Long userId,
            Long eventId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "User with id " + userId)));

        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Event with id " + eventId)));

        List<Request> results = requestRepository.getRequestsByEventAndEventInitiator(event, user);

        return RequestMapper.toListDto(results);
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> getUpdatedRequestStatusEvent(
            EventRequestUpdateStatusDto eventRequestUpdateStatusDto,
            Long userId,
            Long eventId) {
        List<Long> requestIds = eventRequestUpdateStatusDto.getRequestIds();
        List<ParticipationRequestDto> participationRequestDtos = new ArrayList<>();

        for (Long id : requestIds) {
            Request r = requestRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Request with id " + id)));

            if (!r.getStatus().equals(Status.PENDING)) {
                throw new ConflictException(Constants.updateRequestStatusConflict);
            }

            List<Request> requests = requestRepository.findAllByEventAndStatusOrderByCreatedAsc(eventId, Status.CONFIRMED);

            if (Helper.isParticipationLimitLessOrEqualsRequests(r, requests)) {
                throw new ConflictException(Constants.membershipLimitConflict);
            } else {
                r.setStatus(eventRequestUpdateStatusDto.getStatus());
            }

            participationRequestDtos.add(RequestMapper.toParticipationRequestDto(r));
        }
        return participationRequestDtos;
    }
}
