package ru.practicum.explorewithmemain.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.dto.NewCategoryDto;
import ru.practicum.explorewithmemain.entity.Category;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public CategoryDto fromNewCategoryRequestToDto(NewCategoryDto newCategoryDto) {
        return CategoryDto
                .builder()
                .id(null)
                .name(newCategoryDto.getName())
                .build();
    }

    public Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
