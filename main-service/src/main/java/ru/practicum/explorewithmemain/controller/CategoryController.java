package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get(
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Valid @Positive @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        LogHelper.dump(
                Map.of("from", from, "size", size),
                request
        );

        return categoryService.get(from, size);
    }
}
