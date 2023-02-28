package ru.practicum.explorewithmemain.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.dto.UpdateCompilationRequest;
import ru.practicum.explorewithmemain.entity.Compilation;
import ru.practicum.explorewithmemain.service.interfaces.EventService;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    private EventMapper eventMapper;
    private EventService eventService;

    @Autowired
    public CompilationMapper(EventMapper eventMapper, EventService eventService) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
    }

    public CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle(),
                compilation.getEvents()
                    .stream()
                    .map(eventMapper::toShortDto)
                .collect(Collectors.toSet())
        );
    }

    public Compilation toCompilation(UpdateCompilationRequest compilationDto, Long compId) {
        return Compilation
                .builder()
                .id(compId)
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .events(
                        compilationDto.getEvents()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(eventService::getEventById)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto) {
        return Compilation
                .builder()
                .id(null)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(
                        newCompilationDto.getEvents()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(eventService::getEventById)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
