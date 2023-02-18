package ru.practicum.explorewithmemain.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
