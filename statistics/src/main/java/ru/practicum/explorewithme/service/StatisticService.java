package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.entity.Hit;
import ru.practicum.explorewithme.mapper.HitMapper;
import ru.practicum.explorewithme.mapper.StatisticMapper;
import ru.practicum.explorewithme.repository.HitRepository;
import ru.practicum.explorewithme.service.interfaces.StatisticServiceInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticService implements StatisticServiceInterface {
    private final HitRepository hitRepository;
    @Value("${format.date}")
    private String format;

    @Override
    @Transactional
    public HitDto create(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        return HitMapper.toDto(hitRepository.save(hit));
    }

    @Override
    public List<StatisticDto> get(String start, String end, List<String> uris, Boolean unique) {
        List<HitDto> listDto;

        if (Boolean.TRUE.equals(unique)) {
            listDto = calculateUniqueHits(start, end, uris);
        } else {
            listDto = calculateHits(start, end, uris);
        }

        return listDto
                .stream()
                .map(StatisticMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<HitDto> calculateUniqueHits(String start, String end, List<String> uris) {
        log.info("Getting only unique hits...");
        return hitRepository.calculateUniqueHits(
                uris,
                LocalDateTime.parse(start, DateTimeFormatter.ofPattern(format)),
                LocalDateTime.parse(end, DateTimeFormatter.ofPattern(format))
        );
    }

    private List<HitDto> calculateHits(String start, String end, List<String> uris) {
        log.info("Getting all hits...");
        return hitRepository.calculateHits(
                uris,
                LocalDateTime.parse(start, DateTimeFormatter.ofPattern(format)),
                LocalDateTime.parse(end, DateTimeFormatter.ofPattern(format))
        );
    }
}
