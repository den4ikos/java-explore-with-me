package ru.practicum.explorewithmemain.exception;

public class ConflictException extends RuntimeException{
    private String message;

    public ConflictException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
