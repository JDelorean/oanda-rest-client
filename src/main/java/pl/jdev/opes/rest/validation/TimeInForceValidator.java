package pl.jdev.opes.rest.validation;

import pl.jdev.opes_commons.domain.order.TimeInForce;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class TimeInForceValidator implements ConstraintValidator<SupportedTimeInForce, TimeInForce> {
    @Override
    public void initialize(SupportedTimeInForce constraintAnnotation) {
    }

    @Override
    public boolean isValid(TimeInForce value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(TimeInForce.values()).contains(value);
    }
}
