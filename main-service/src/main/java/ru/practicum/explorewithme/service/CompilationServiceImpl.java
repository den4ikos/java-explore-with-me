package ru.practicum.explorewithme.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.helper.PaginationHelper;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.service.interfaces.CompilationService;

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
}
