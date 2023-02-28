package ru.practicum.explorewithmemain.exception;

public class AlreadyExistsException extends RuntimeException {
    private String message;
    public AlreadyExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
