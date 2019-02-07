package pl.jdev.opes.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UnableToSyncException extends RuntimeException {
    public UnableToSyncException(String errorMessage) {
        super(errorMessage);
    }
}
