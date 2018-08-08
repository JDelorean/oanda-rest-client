package pl.jdev.oanda_rest_client.rest.validation;

import pl.jdev.oanda_rest_client.domain.order.TimeInForce;

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
