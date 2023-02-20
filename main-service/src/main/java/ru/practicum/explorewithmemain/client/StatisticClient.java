package ru.practicum.explorewithmemain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.explorewithmemain.dto.HitDto;
import ru.practicum.explorewithmemain.dto.ViewStatisticDto;

import java.util.List;

@FeignClient(value = "stats", url = "http://localhost:9090")
public interface StatisticClient {
    @GetMapping(value = "/stats?start={start}&end={end}&uris={uris}&unique={unique}")
    List<ViewStatisticDto> getStatistics(@PathVariable String start, @PathVariable String end, @PathVariable String[] uris, @PathVariable Boolean unique);

    @PostMapping(value = "/hit")
    void setEventHit(@RequestBody HitDto hitDto);
}
