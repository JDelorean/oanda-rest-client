package pl.jdev.oanda_rest_client.rest.validation;

import pl.jdev.oanda_rest_client.domain.transaction.TransactionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class TransactionTypeValidator implements ConstraintValidator<SupportedTransactionType, TransactionType> {
    @Override
    public void initialize(SupportedTransactionType constraintAnnotation) {

    }

    @Override
    public boolean isValid(TransactionType value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(TransactionType.values()).contains(value.toString());
    }
}
