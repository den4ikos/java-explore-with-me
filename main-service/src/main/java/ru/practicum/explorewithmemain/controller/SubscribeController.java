package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;
import ru.practicum.explorewithmemain.helper.SubscriptionHelper;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/subscribers")
public class SubscribeController {
    private final SubscriberService subscriberService;

    @GetMapping(value = "/get/{id}")
    public SubscribeDto findSubscriptionById(@PathVariable Long id, HttpServletRequest request) {
        LogHelper.dump(Map.of("id", id), request);
        return subscriberService.getSubscribeById(id);
    }

    @GetMapping(value = "/signatory/{signatoryId}")
    public SubscribeDto findSubscriptionBySignatoryId(@PathVariable Long signatoryId, HttpServletRequest request) {
        LogHelper.dump(Map.of("signatoryId", signatoryId), request);
        return subscriberService.getSubscribeBySignatoryId(signatoryId);
    }

    @GetMapping(value = "/event/{eventId}")
    public SubscribeDto findSubscriptionByEventId(@PathVariable Long eventId, HttpServletRequest request) {
        LogHelper.dump(Map.of("eventId", eventId), request);
        return subscriberService.getSubscribeByEventId(eventId);
    }

    @GetMapping(value = "/subscriber/{subscriberId}")
    public SubscribeDto getSubscriber(@PathVariable Long subscriberId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriberId", subscriberId), request);
        return subscriberService.getSubscriber(subscriberId);
    }

    @PostMapping(value = "/{subscriberId}/request/{signatoryId}/event/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
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

    @PatchMapping(value = "/{id}/signatory/{signatoryId}/event/{eventId}/confirm")
    public SubscribeDto confirmSubscription(
            @PathVariable Long id,
            @PathVariable Long signatoryId,
            @PathVariable Long eventId,
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        SubscriberStatus statusForUpdate = SubscriptionHelper.convertFromString(status);

        LogHelper.dump(Map.of(
                "id", id,
                "signatoryId", signatoryId,
                "eventId", eventId,
                "status", statusForUpdate
        ), request);
        return subscriberService.updateSubscriptionStatue(id, signatoryId, eventId, statusForUpdate);
    }
}
