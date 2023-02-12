package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.Statistic;
import ru.practicum.explorewithme.log.LogHelper;
import ru.practicum.explorewithme.service.StatisticService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping(value = "/stats")
    public List<Statistic> stats(
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

        return null;
    }
}
