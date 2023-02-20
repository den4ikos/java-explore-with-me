package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.dto.NewUserRequestDto;
import ru.practicum.explorewithmemain.dto.UserDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.mapper.UserMapper;
import ru.practicum.explorewithmemain.service.interfaces.CompilationService;
import ru.practicum.explorewithmemain.service.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final CompilationService compilationService;
    private final UserService userService;

    @PostMapping(value = "/compilations")
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("newCompilationDto", newCompilationDto),
                request
        );

        return compilationService.create(newCompilationDto);
    }

    @DeleteMapping(value = "/compilations/{compId}")
    public void deleteCompilationByCompId(@PathVariable Long compId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("compId", compId),
                request
        );

        compilationService.delete(compId);
    }

    @GetMapping(value = "/users")
    public List<UserDto> getUsersByParams(
            @RequestParam(value = "ids", required = false) Long[] ids,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
            @Valid @Positive @RequestParam(defaultValue = "10", required = false) int size,
            HttpServletRequest request
    ) {
        LogHelper.dump(
                Map.of("ids", ids, "from", from, "size", size),
                request
        );

        return userService.getUsers(Map.of("ids", ids, "from", from, "size", size));
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody NewUserRequestDto newUserRequestDto, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("newUserRequest", newUserRequestDto),
                request
        );

        return userService.createUser(UserMapper.fromNewUserRequestToUserDto(newUserRequestDto));
    }

    @DeleteMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("userId", userId),
                request
        );

        userService.deleteUser(userId);
    }
}
