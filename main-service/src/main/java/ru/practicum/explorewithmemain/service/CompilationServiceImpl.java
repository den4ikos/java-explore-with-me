package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.entity.Compilation;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.mapper.CompilationMapper;
import ru.practicum.explorewithmemain.repository.CompilationRepository;
import ru.practicum.explorewithmemain.service.interfaces.CompilationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    public List<CompilationDto> get(Boolean pinned, int from, int size) {

        if (null == pinned) {
            return compilationRepository.findAll(PageRequest.of( (from / size), size ))
                    .stream()
                    .map(compilationMapper::toDto)
                    .collect(Collectors.toList());
        }

        return compilationRepository.findByPinnedOrderByIdDesc(pinned, PageRequest.of( (from / size), size ))
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getById(Long id) {
        Compilation compilation =
                compilationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id: " + id + " not found"));
        return compilationMapper.toDto(compilation);
    }
}
