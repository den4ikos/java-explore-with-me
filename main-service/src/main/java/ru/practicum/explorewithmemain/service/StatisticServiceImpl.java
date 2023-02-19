package ru.practicum.explorewithmemain.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.client.StatisticClient;
import ru.practicum.explorewithmemain.dto.HitDto;
import ru.practicum.explorewithmemain.dto.ViewStatisticDto;
import ru.practicum.explorewithmemain.exception.BadRequestException;
import ru.practicum.explorewithmemain.service.interfaces.StatisticService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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

    @Override
    public void addEventHits(Map<String, String> params) {
        if (!params.containsKey("ip")) {
            throw new BadRequestException("IP parameter is required!");
        }
        String ip = params.get("ip");

        if (!params.containsKey("uri")) {
            throw new BadRequestException("URI parameter is required!");
        }
        String uri = params.get("uri");

        HitDto hitDto = HitDto
                .builder()
                .id(null)
                .app(Constants.app)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();

        statisticClient.setEventHit(hitDto);
    }
}
