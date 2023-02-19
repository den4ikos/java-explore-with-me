package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final CompilationService compilationService;

    @PostMapping(value = "/compilations")
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("newCompilationDto", newCompilationDto),
                request
        );

        return compilationService.create(newCompilationDto);
    }
}
