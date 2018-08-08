package pl.jdev.oanda_rest_client.rest.validation;

import pl.jdev.oanda_rest_client.domain.order.OrderType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Arrays.asList;

public class OrderTypeValidator implements ConstraintValidator<SupportedOrderType, OrderType> {

    @Override
    public void initialize(SupportedOrderType constraintAnnotation) {
    }

    @Override
    public boolean isValid(OrderType value, ConstraintValidatorContext context) {
        return value != null && asList(OrderType.values()).contains(value);
    }
}