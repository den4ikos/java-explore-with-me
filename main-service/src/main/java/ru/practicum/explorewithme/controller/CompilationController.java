package ru.practicum.explorewithme.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.helper.LogHelper;
import ru.practicum.explorewithme.service.interfaces.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
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
                Map.of("pinned", pinned, "from", from, "size", size),
                request
        );

        return compilationService.get(pinned, from, size);
    }
}
