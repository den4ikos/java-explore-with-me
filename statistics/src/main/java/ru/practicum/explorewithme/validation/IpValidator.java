package ru.practicum.explorewithme.validation;

import ru.practicum.explorewithme.annotation.IpCheckConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpValidator implements ConstraintValidator<IpCheckConstraint, String> {
    @Override
    public void initialize(IpCheckConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        System.out.println("field " + field);
        System.out.println("cxt " + cxt);
        if (field == null) return false;

        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
        String regex = zeroTo255 + "\\."+ zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(cxt.toString());

        return m.matches();
    }
}
