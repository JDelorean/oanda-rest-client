package pl.jdev.opes.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {
    private static final long serialVersionUID = 7208251494550861723L;

    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String errorMessage) {
        super(errorMessage);
    }
}
