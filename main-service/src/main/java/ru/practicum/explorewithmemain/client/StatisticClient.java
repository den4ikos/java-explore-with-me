package ru.practicum.explorewithmemain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.HitDto;
import ru.practicum.explorewithmemain.dto.ViewStatisticDto;

import java.util.List;

@FeignClient(value = "stats", url = "http://statistics-server:9090")
public interface StatisticClient {
    @RequestMapping(method = RequestMethod.GET, value = "/stats?start={start}&end={end}&uris={uris}&unique={unique}")
    List<ViewStatisticDto> getStatistics(@PathVariable String start, @PathVariable String end, @PathVariable String[] uris, @PathVariable Boolean unique);

    @RequestMapping(method = RequestMethod.POST, value = "/hit")
    void setEventHit(@RequestBody HitDto hitDto);
}
