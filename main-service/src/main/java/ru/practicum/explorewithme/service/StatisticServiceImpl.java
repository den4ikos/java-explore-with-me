package ru.practicum.explorewithme.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.Constants;
import ru.practicum.explorewithme.client.StatisticClient;
import ru.practicum.explorewithme.dto.ViewStatisticDto;
import ru.practicum.explorewithme.service.interfaces.StatisticService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private StatisticClient statisticClient;

    @Override
    public Long countViews(String uri) {
        List<ViewStatisticDto> viewStatisticDto = statisticClient
                .getStatistics(
                        // start
                        LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(Constants.dateFormat)),
                        // end
                        LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ofPattern(Constants.dateFormat)),
                        // uris
                        new String[]{uri},
                        // unique
                        false
                )
        ;

        if (viewStatisticDto == null || viewStatisticDto.size() == 0) return 0L;

        return viewStatisticDto
                .stream()
                .filter(c -> Constants.app.equals(c.getApp()))
                .collect(Collectors.toList())
                .get(0)
                .getHits();
    }
}
