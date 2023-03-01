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

    @PostMapping(value = "/{userId}/request")
    public SubscribeDto sendRequest(@PathVariable Long userId, HttpServletRequest request) {
        LogHelper.dump(Map.of("userId", userId), request);
        return subscriberService.sendRequest(userId);
    }
}
