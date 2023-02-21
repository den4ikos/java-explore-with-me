package ru.practicum.explorewithmemain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.dto.NewEventDto;
import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;
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
    public ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("userId", userId, "eventId", eventId),
                request
        );

        return userService.createUserRequest(userId, eventId);
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
    public EventFullDto createUserEvent(@Valid @RequestBody NewEventDto newEventDto, @PathVariable Long userId, HttpServletRequest request) {
        LogHelper.dump(Map.of("userId", userId, "newEventDto", newEventDto), request);
        return userService.createUserEvent(newEventDto, userId);
    }

}
