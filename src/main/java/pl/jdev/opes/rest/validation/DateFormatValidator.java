package pl.jdev.opes.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DateFormatValidator implements ConstraintValidator<IsoFormat, String> {
    @Autowired
    private SimpleDateFormat df;

    @Override
    public void initialize(IsoFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            df.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
