package ru.practicum.explorewithmemain.mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.entity.Compilation;

import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    private EventMapper eventMapper;

    @Autowired
    public CompilationMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
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

    public Compilation toCompilation(CompilationDto compilationDto) {
        return null;
    }
}
