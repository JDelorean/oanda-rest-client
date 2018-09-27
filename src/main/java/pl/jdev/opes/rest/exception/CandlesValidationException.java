package pl.jdev.opes.rest.exception;

public class CandlesValidationException extends Exception {
    public CandlesValidationException(String errorMessage) {
        super(errorMessage);
    }
}
