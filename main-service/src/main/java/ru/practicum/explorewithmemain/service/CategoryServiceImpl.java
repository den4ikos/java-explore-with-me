package ru.practicum.explorewithmemain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.entity.Category;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.repository.CategoryRepository;
import ru.practicum.explorewithmemain.service.interfaces.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> get(int from, int size) {
        return categoryRepository
                .findAll(PageRequest.of( (from/size), size ))
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
        return categoryMapper.toDto(category);
    }
}
