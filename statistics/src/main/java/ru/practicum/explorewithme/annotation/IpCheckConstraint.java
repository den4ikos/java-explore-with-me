package ru.practicum.explorewithme.annotation;

import ru.practicum.explorewithme.validation.IpValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IpCheckConstraint {
    String message() default "Invalid IP address!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
