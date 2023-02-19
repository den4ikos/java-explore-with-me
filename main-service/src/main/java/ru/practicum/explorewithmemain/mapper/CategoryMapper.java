package ru.practicum.explorewithmemain.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.entity.Category;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
