package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithmemain.dto.EventShortDto;
import ru.practicum.explorewithmemain.helper.DateWorkHelper;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.helper.Sort;
import ru.practicum.explorewithmemain.service.interfaces.EventService;
import ru.practicum.explorewithmemain.service.interfaces.StatisticService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/events")
public class EventController {
    private final EventService eventService;
    private final StatisticService statisticService;

    @Autowired
    public EventController(EventService eventService, StatisticService statisticService) {
        this.eventService = eventService;
        this.statisticService = statisticService;
    }

    @GetMapping
    public List<EventShortDto> get(
            @RequestParam(required = false, defaultValue = "") String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) Sort sort,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Valid @Positive @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
            ) {

        LocalDateTime start = DateWorkHelper.makeDateFromRequest("start", rangeStart);
        LocalDateTime end = DateWorkHelper.makeDateFromRequest("end", rangeEnd);
        Map<String, Object> data = new HashMap<>();
        if (null != text) {
            data.put("text", text);
        }

        if (null != categories) {
            data.put("categories", categories);
        }

        if (null != paid) {
            data.put("paid", paid);
        }

        data.put("start", start);

        data.put("end", end);

        if (null != sort) {
            data.put("sort", sort);
        }

        data.put("onlyAvailable", onlyAvailable);
        data.put("from", from);
        data.put("size", size);

        LogHelper.dump(data, request);

        statisticService.addEventHits(Map.of(
                "ip", request.getRemoteAddr(),
                "uri", request.getRequestURI()
        ));

//        return eventService.get(data);
        return null;
    }
}
