package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.CompilationDto;
import ru.practicum.explorewithmemain.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.dto.UpdateCompilationDto;
import ru.practicum.explorewithmemain.entity.Compilation;
import ru.practicum.explorewithmemain.exception.AlreadyExistsException;
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

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        try {
            Compilation compilation = compilationRepository.save(compilationMapper.newCompilationDtoToCompilation(newCompilationDto));
            return compilationMapper.toDto(compilation);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException(String.format(Constants.alreadyExists, "Compilation", newCompilationDto.getTitle()));
        }
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        try {
            compilationRepository.deleteById(compId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Compilation"));
        }
    }

    @Override
    @Transactional
    public CompilationDto update(CompilationDto compilationDto, Long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new NotFoundException(String.format(Constants.notFoundError, "Compilation " + compId));
        }
        compilationDto.setId(compId);
        Compilation compilation = compilationMapper.toCompilation(compilationDto);
        return compilationMapper.toDto(compilationRepository.save(compilation));
    }
}
