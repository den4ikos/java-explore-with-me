package ru.practicum.explorewithmemain;

public class Constants {
    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String app = "explore-with-me-statistics";
    public static final String initiatorConflictMessage = "The initiator can't add a request to participate in his event";
    public static final String eventConflictMessage = "You can't participate in an unpublished event";
    public static final String userNotFound = "User with id %o not found";
    public static final String eventNotFound = "Event with id %o not found";
    public static final String alreadyExists = "%s with name '%s' already exists";
    public static final String notFoundError = "%s not found";
    public static final String emailAlreadyExists = "User with email '%s' already exists";
    public static final String badRequest = "Bad Request!";
    public static final String eventDateError = "The end date of the event can't be earlier than the event's start date.";
    public static final String initiatorCanceled = "Only the event's initiator can cancel it";
    public static final String eventCanceledConflict = "Only events with pending status can be canceled";
    public static final String eventPublishedConflict = "Only events with pending status can be published";
    public static final String categoryEventsCount = "You can not remove this category";
    public static final String eventSubscribeConflictStatus = "You can't confirm or reject unpublished event";
    public static final String startDateEarlier = "The start date of the event to be changed must be no earlier than one hour from the publication date";
    public static final String updateRequestStatusConflict = "Status can only be changed for applications that are in the pending state";
    public static final String membershipLimitConflict = "Membership limit reached";
    public static final String dateScheduleConflict = "The date and time for which the event is scheduled cannot be earlier than two hours from the current moment";
    public static final String addSubscriptionToUnpublishedEventError = "You can't subscribe to an unpublished event";
    public static final String signatorySubscriptionConflict = "You have no permission to this action";
}
