package ru.practicum.explorewithmemain.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.dto.EventFullDto;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.helper.LogHelper;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/subscribers")
public class SubscribeController {
    private final SubscriberService subscriberService;

    @GetMapping(value = "/subscriber/{subscriberId}/get")
    public List<SubscribeDto> getAllBySubscriberId(@PathVariable Long subscriberId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriberId", subscriberId), request);
        return subscriberService.getAllBySubscriber(subscriberId);
    }

    @GetMapping(value = "/event-owner/{signatoryId}/get")
    public List<SubscribeDto> getAllBySignatoryId(@PathVariable Long signatoryId, HttpServletRequest request) {
        LogHelper.dump(Map.of("signatoryId", signatoryId), request);
        return subscriberService.getAllBySignatory(signatoryId);
    }

    @GetMapping(value = "/subscriber/{subscriberId}/events/get")
    public List<EventFullDto> getSubscriberEvents(@PathVariable Long subscriberId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriberId", subscriberId), request);
        return subscriberService.getSubscriberEvents(subscriberId);
    }

    @GetMapping(value = "/get/{id}")
    public SubscribeDto getSubscriptionById(@PathVariable Long id, HttpServletRequest request) {
        LogHelper.dump(Map.of("id", id), request);
        return subscriberService.getSubscribeById(id);
    }

    @GetMapping(value = "/event-owner/{signatoryId}")
    public SubscribeDto getSubscriptionByEventOwnerId(@PathVariable Long signatoryId, HttpServletRequest request) {
        LogHelper.dump(Map.of("signatoryId", signatoryId), request);
        return subscriberService.getSubscribeByEventOwnerId(signatoryId);
    }

    @GetMapping(value = "/event/{eventId}")
    public SubscribeDto getSubscriptionByEventId(@PathVariable Long eventId, HttpServletRequest request) {
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

    @PatchMapping(value = "/{id}/event-owner/{signatoryId}/event/{eventId}/confirm")
    public SubscribeDto confirmSubscription(
            @PathVariable Long id,
            @PathVariable Long signatoryId,
            @PathVariable Long eventId,
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        SubscriberStatus statusForUpdate = SubscriberStatus.convertFromString(status);

        LogHelper.dump(Map.of(
                "id", id,
                "signatoryId", signatoryId,
                "eventId", eventId,
                "status", statusForUpdate
        ), request);
        return subscriberService.updateSubscriptionStatue(id, signatoryId, eventId, statusForUpdate);
    }

    @PatchMapping(value = "/{id}/{subscriberId}/reject")
    public SubscribeDto rejectSubscription(@PathVariable(name = "id") Long subscriptionId, @PathVariable Long subscriberId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriberId", subscriptionId), request);
        return subscriberService.rejectSubscription(subscriptionId, subscriberId);
    }

    @DeleteMapping(value = "/{subscriptionId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> deleteSubscription(@PathVariable Long subscriptionId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriptionId", subscriptionId), request);
        return subscriberService.deleteSubscription(subscriptionId);
    }

    @DeleteMapping(value = "/subscriber/{subscriberId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> deleteAllBySubscriber(@PathVariable Long subscriberId, HttpServletRequest request) {
        LogHelper.dump(Map.of("subscriberId", subscriberId), request);
        return subscriberService.deleteAllBySubscriber(subscriberId);
    }
}
