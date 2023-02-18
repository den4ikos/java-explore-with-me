package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(int from, int size);
}
