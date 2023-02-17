package ru.practicum.explorewithme.validation;

import org.springframework.beans.factory.annotation.Value;
import ru.practicum.explorewithme.annotation.LocalDateTimeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocalDateTimeValidator implements ConstraintValidator<LocalDateTimeConstraint, String> {
    @Value("${format.date}")
    private String format;

    @Override
    public void initialize(LocalDateTimeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        return false;
    }
}
