package ru.practicum.explorewithmemain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomFieldsException {
    private String field;
    private String message;
}
