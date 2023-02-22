package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.dto.NewCategoryDto;
import ru.practicum.explorewithmemain.entity.Category;
import ru.practicum.explorewithmemain.exception.AlreadyExistsException;
import ru.practicum.explorewithmemain.exception.ConflictException;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.repository.CategoryRepository;
import ru.practicum.explorewithmemain.service.interfaces.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        try {
            Category category = categoryRepository.save(categoryMapper.toCategory(categoryDto));
            return categoryMapper.toDto(category);
        } catch (DataIntegrityViolationException e) {
            log.error("ERROR from category create " + e.getMessage());
            throw new ConflictException(String.format(Constants.alreadyExists, "Category", categoryDto.getName()));
        }
    }

    @Override
    public CategoryDto mapHelperToDto(NewCategoryDto categoryDto) {
        return categoryMapper.fromNewCategoryRequestToDto(categoryDto);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        Category c = categoryRepository
                .findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Category")));

        if (null != categoryDto.getName()) {
            c.setName(categoryDto.getName());
        }

        Category savedCat = categoryRepository.save(c);
        return categoryMapper.toDto(savedCat);
    }

    @Override
    @Transactional
    public void deleteCategory(Long catId) {
        Long eventsCategory = categoryRepository.countAllRelatedEvents(catId);

        if (eventsCategory > 0) {
            throw new ConflictException(Constants.categoryEventsCount);
        }
        try {
            categoryRepository.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Category"));
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long catId, NewCategoryDto categoryDto) {
        Category c = categoryRepository
                .findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "Category")));

        throw new ConflictException("CONFLICT");
    }
}
