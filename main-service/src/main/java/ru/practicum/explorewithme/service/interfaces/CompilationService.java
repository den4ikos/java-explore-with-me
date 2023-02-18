package ru.practicum.explorewithme.service.interfaces;

import ru.practicum.explorewithme.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> get(Boolean pinned, int from, int size);
}
