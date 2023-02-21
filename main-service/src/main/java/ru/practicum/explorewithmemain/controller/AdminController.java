package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.*;
import ru.practicum.explorewithmemain.helper.DateWorkHelper;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.helper.State;
import ru.practicum.explorewithmemain.mapper.UserMapper;
import ru.practicum.explorewithmemain.service.interfaces.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final CompilationService compilationService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final StatisticService statisticService;

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

    @PostMapping(value = "/categories")
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("newCategoryDto", newCategoryDto),
                request
        );

        return categoryService.create(categoryService.mapHelperToDto(newCategoryDto));
    }

    @PatchMapping(value = "/categories")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        LogHelper.dump(Map.of("categoryDto", categoryDto), request);
        return categoryService.update(categoryDto);
    }

    @GetMapping(value = "/events")
    public List<EventFullDto> get(
            @RequestParam(required = false) Set<Long> users,
            @RequestParam(required = false) Set<State> states,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Valid @Positive @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {

        LocalDateTime start = DateWorkHelper.makeDateFromRequest("start", rangeStart);
        LocalDateTime end = DateWorkHelper.makeDateFromRequest("end", rangeEnd);
        Map<String, Object> data = new HashMap<>();
        if (null != users) {
            data.put("users", users);
        }

        if (null != states) {
            data.put("states", states);
        }

        if (null != categories) {
            data.put("categories", categories);
        }

        data.put("start", start);
        data.put("end", end);
        data.put("from", from);
        data.put("size", size);

        LogHelper.dump(data, request);

        return eventService.get(data);
    }
}
