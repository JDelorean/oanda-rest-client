package pl.jdev.opes.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class RequestFailedException extends RuntimeException {
    private static final long serialVersionUID = 6327378856513677419L;

    public RequestFailedException(String errorMessage) {
        super(errorMessage);
    }
}
