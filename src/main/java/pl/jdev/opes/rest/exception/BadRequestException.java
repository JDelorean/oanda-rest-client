package pl.jdev.opes.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -6552851652091080439L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
