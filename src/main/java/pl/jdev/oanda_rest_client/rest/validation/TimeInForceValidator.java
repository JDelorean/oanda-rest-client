package pl.jdev.oanda_rest_client.rest.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pl.jdev.oanda_rest_client.domain.order.Order;

public class TimeInForceValidator implements ConstraintValidator<SupportedTimeInForce, Order.TimeInForce> {

	@Override
	public void initialize(SupportedTimeInForce constraintAnnotation) {
	}

	@Override
	public boolean isValid(Order.TimeInForce value, ConstraintValidatorContext context) {
		return value != null && Arrays.asList(Order.TimeInForce.values()).contains(value.toString());
	}

}
