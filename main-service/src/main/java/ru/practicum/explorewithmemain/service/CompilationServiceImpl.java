package ru.practicum.explorewithmemain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.entity.Compilation;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.helper.PaginationHelper;
import ru.practicum.explorewithmemain.mapper.CompilationMapper;
import ru.practicum.explorewithmemain.repository.CompilationRepository;
import ru.practicum.explorewithmemain.service.interfaces.CompilationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private CompilationRepository compilationRepository;
    private PaginationHelper paginationHelper;
    private CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> get(Boolean pinned, int from, int size) {
        Pageable pageable = paginationHelper.getPagination(from, size, "id", Sort.Direction.DESC);

        if (null == pinned) {
            return compilationRepository.findAll(pageable)
                    .stream()
                    .map(compilationMapper::toDto)
                    .collect(Collectors.toList());
        }

        return compilationRepository.findAllByPinned(pinned, pageable)
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long id) {
        Compilation compilation = compilationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id: " + id + " not found"));
        return compilationMapper.toDto(compilation);
    }
}
