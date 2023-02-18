package ru.practicum.explorewithmemain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.explorewithmemain.dto.ViewStatisticDto;

import java.util.List;

@FeignClient(value = "stats")
public interface StatisticClient {
    @GetMapping(value = "/stats?start={start}&end={end}&uris={uris}&unique={unique}")
    List<ViewStatisticDto> getStatistics(@PathVariable String start, @PathVariable String end, @PathVariable String[] uris, @PathVariable Boolean unique);
}
