package ru.practicum.explorewithmemain.helper;

import java.util.Arrays;
import java.util.List;

public class SubscriptionHelper {
    public static List<State> getAllowedStates() {
        State[] statesArray = new State[]{State.PUBLISHED, State.PUBLISH_EVENT};
        return Arrays.asList(statesArray);
    }
}
