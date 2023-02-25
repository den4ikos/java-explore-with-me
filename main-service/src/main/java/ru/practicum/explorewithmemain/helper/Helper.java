package ru.practicum.explorewithmemain.helper;

import ru.practicum.explorewithmemain.entity.Request;

import java.util.List;

public class Helper {
    public static boolean isParticipationLimitLessOrEqualsRequests(Request request, List<Request> requests) {
        if (request == null) {
            return true;
        }
        return request.getEvent().getParticipantLimit() <= requests.size();
    }
}
