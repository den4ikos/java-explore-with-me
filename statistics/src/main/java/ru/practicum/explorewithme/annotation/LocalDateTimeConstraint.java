package ru.practicum.explorewithme.annotation;

import ru.practicum.explorewithme.validation.LocalDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateTimeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface LocalDateTimeConstraint {
    String message() default "Enter correct format for the date (Year-month-date)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
