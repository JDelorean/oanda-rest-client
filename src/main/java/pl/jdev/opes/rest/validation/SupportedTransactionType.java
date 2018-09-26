package pl.jdev.opes.rest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedTransactionType {
    String message() default "Invalid time in force code.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
