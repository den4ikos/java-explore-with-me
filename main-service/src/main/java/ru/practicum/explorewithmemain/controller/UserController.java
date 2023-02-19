package ru.practicum.explorewithmemain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.ParticipationRequestDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
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
}
