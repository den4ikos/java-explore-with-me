package ru.practicum.explorewithmemain.helper;

import ru.practicum.explorewithmemain.exception.BadRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SubscriptionHelper {
    public static List<State> getAllowedStates() {
        State[] statesArray = new State[]{State.PUBLISHED, State.PUBLISH_EVENT};
        return Arrays.asList(statesArray);
    }

    public static SubscriberStatus convertFromString(String status) {
        String stringStatusToUpper = status == null ? "CONFIRMED" : status.toUpperCase(Locale.ROOT);

        if (!containsEnumStatus(status)) {
            throw new BadRequestException("Set correct status! Must be confirmed or rejected.");
        }

        return SubscriberStatus.valueOf(stringStatusToUpper);
    }

    private static boolean containsEnumStatus(String statusValue) {
        for (SubscriberStatus s: SubscriberStatus.values()) {
            if (s.name().equals(statusValue)) {
                return true;
            }
        }

        return false;
    }
}
