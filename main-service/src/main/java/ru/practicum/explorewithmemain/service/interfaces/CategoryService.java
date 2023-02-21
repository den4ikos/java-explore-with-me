package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(int from, int size);

    CategoryDto getById(Long id);

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto mapHelperToDto(NewCategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void deleteCategory(Long catId);
}
