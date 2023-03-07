package ru.practicum.explorewithmemain.helper;

import java.util.Arrays;
import java.util.List;

/**
 * Class helper for subscriptions
 * @author den4ikos
 */
public class SubscriptionHelper {

    /**
     * @return all allowed states for the published event
     * @see State
     */
    public static List<State> getAllowedStates() {
        State[] statesArray = new State[]{State.PUBLISHED, State.PUBLISH_EVENT};
        return Arrays.asList(statesArray);
    }
}
