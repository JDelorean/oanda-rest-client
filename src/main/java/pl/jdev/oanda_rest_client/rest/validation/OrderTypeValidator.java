package pl.jdev.oanda_rest_client.rest.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pl.jdev.oanda_rest_client.domain.order.Order.OrderType;

public class OrderTypeValidator implements ConstraintValidator<SupportedOrderType, OrderType> {

	@Override
	public void initialize(SupportedOrderType constraintAnnotation) {
	}

	@Override
	public boolean isValid(OrderType value, ConstraintValidatorContext context) {
		return value != null && Arrays.asList(OrderType.values()).contains(value.toString());
	}
}