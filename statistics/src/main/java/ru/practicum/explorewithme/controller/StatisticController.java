package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatisticDto;
import ru.practicum.explorewithme.helper.LogHelper;
import ru.practicum.explorewithme.service.interfaces.StatsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class StatisticController {

    private final StatsService statisticService;

    @PostMapping(value = "/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public HitDto hit(@RequestBody HitDto hitDto, HttpServletRequest request) {
        LogHelper.dump(
                Map.of("hitDto", hitDto),
                request
        );
        return statisticService.create(hitDto);
    }

    @GetMapping(value = "/stats")
    public List<StatisticDto> stats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique,
            HttpServletRequest request
    ) {
        LogHelper.dump(
                Map.of("start", start, "end", end, "uris", uris.toString(), "unique", unique),
                request
        );

        return statisticService.get(start, end, uris, unique);
    }
}
