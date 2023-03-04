package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/subscribers")
public class SubscribeController {
    private final SubscriberService subscriberService;

    @PostMapping(value = "/{subscriberId}/request/{signatoryId}/event/{eventId}")
    public SubscribeDto sendRequest(
            @PathVariable Long subscriberId,
            @PathVariable Long signatoryId,
            @PathVariable Long eventId,
            HttpServletRequest request) {
        LogHelper.dump(Map.of(
                "subscriberId", subscriberId,
                "signatoryId", signatoryId,
                "eventId", eventId
        ), request);
        return subscriberService.sendRequest(subscriberId, signatoryId, eventId);
    }
}
