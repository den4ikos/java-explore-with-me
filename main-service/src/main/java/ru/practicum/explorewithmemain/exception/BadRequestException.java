package ru.practicum.explorewithmemain.exception;

public class BadRequestException extends RuntimeException{
    private String message;
    public BadRequestException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
