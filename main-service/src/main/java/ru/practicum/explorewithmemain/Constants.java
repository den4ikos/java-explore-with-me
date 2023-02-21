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
}
