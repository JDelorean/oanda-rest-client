package pl.jdev.opes.rest.validation;

import pl.jdev.opes_commons.domain.instrument.Instrument;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InstrumentValidator
        implements ConstraintValidator<SupportedInstrument, Instrument> {
    @Override
    public void initialize(SupportedInstrument constraintAnnotation) {
    }

    @Override
    public boolean isValid(Instrument value,
                           ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (!isMatchingInstrumentPattern(value)) {
            return false;
        }
        if (!hasSupportedCurrencyCodes(value)) {
            return false;
        }
        return true;
    }

    private boolean hasSupportedCurrencyCodes(Instrument value) {
        // TODO impl
        return true;
    }

    private boolean isMatchingInstrumentPattern(Instrument value) {
        // TODO impl
        return true;
    }
}
