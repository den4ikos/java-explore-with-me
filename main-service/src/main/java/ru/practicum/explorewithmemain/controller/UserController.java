package ru.practicum.explorewithmemain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.*;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("userId", userId),
                request
        );

        return userService.getUserRequests(userId);
    }

    @PostMapping(value = "/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("userId", userId, "eventId", eventId),
                request
        );

        return userService.createUserRequest(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelUserEventRequest(@PathVariable Long userId, @PathVariable Long requestId, HttpServletRequest request) {
        LogHelper.dump(Map.of("userId", userId, "requestId", requestId), request);
        return userService.cancelUserEventRequest(userId, requestId);
    }

    @GetMapping(value = "/{userId}/events")
    public List<EventShortDto> getUserEvents(
        @PathVariable Long userId,
        @Valid @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
        @Valid @Positive @RequestParam(defaultValue = "10", required = false) int size,
        HttpServletRequest request
    ) {
        LogHelper.dump(
                Map.of("userId", userId, "from", from, "size", size),
                request
        );

        return userService.getUserEvents(Map.of("userId", userId, "from", from, "size", size));
    }

    @PostMapping(value = "/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createUserEvent(@Valid @RequestBody NewEventDto newEventDto, @PathVariable Long userId, HttpServletRequest request) {
        LogHelper.dump(Map.of("userId", userId, "newEventDto", newEventDto), request);
        return userService.createUserEvent(newEventDto, userId);
    }

    @GetMapping(value = "/{userId}/events/{eventId}")
    public EventFullDto getFullUserEvent(@PathVariable Long userId, @PathVariable Long eventId, HttpServletRequest request) {
        LogHelper.dump(Map.of("userId", userId, "eventId", eventId), request);
        return userService.getFullUserEventInfo(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequestDto updateEventUserRequestDto,
            HttpServletRequest request) {
        LogHelper.dump(Map.of("updateEventUserRequestDto", updateEventUserRequestDto, "userId", userId, "eventId", eventId), request);
        return userService.cancelEvent(userId, eventId, updateEventUserRequestDto);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventAction(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            HttpServletRequest request) {
        LogHelper.dump(
                Map.of( "userId", userId, "eventId", eventId),
                request
        );
        return userService.getEventRequestStatusUpdatedResult(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests")
    public Map<String, List<ParticipationRequestDto>> changeRequestUserStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody(required = false) EventRequestUpdateStatusDto eventRequestUpdateStatusDto,
            HttpServletRequest request) {
        if (null == eventRequestUpdateStatusDto) {
            throw new ConflictException("");
        }
        LogHelper.dump(
                Map.of( "userId", userId, "eventId", eventId),
                request
        );
        return userService.getUpdatedRequestStatusEvent(eventRequestUpdateStatusDto, userId, eventId);
    }
}
