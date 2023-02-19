package ru.practicum.explorewithmemain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> get(
        @Valid @RequestParam(required = false) Boolean pinned,
        @Valid @PositiveOrZero @RequestParam(defaultValue = "0") int from,
        @Valid @Positive @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request
    ) {
        LogHelper.dump(
                Map.of("pinned", null == pinned ? "" : pinned, "from", from, "size", size),
                request
        );

        return compilationService.get(pinned, from, size);
    }

    @GetMapping(value = "/{compId}")
    public CompilationDto getById(@PathVariable Long compId, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("compId", compId),
                request
        );

        return compilationService.getById(compId);
    }
}
