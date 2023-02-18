package ru.practicum.explorewithmemain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.dto.CategoryDto;
import ru.practicum.explorewithmemain.helper.PaginationHelper;
import ru.practicum.explorewithmemain.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.repository.CategoryRepository;
import ru.practicum.explorewithmemain.service.interfaces.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private PaginationHelper paginationHelper;
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> get(int from, int size) {
        Pageable pageable = paginationHelper.getPagination(from, size, "id", Sort.Direction.ASC);
        return categoryRepository
                .findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
