package pl.jdev.opes.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleIOException(HttpServletRequest request, Exception ex) {
        return ex.getMessage();
    }

}
