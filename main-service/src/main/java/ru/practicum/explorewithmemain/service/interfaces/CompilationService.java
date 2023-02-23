package ru.practicum.explorewithmemain.service.interfaces;

import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> get(Boolean pinned, int from, int size);

    CompilationDto getById(Long id);

    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long compId);

    CompilationDto update(UpdateCompilationRequest compilationDto, Long compId);
}
