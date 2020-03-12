package de.bauhaus.digital.exception;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomConstraintException extends RuntimeException {

    private final ConstraintViolationException constraintViolationException;

    public CustomConstraintException(String message, ConstraintViolationException cause) {
        super(message, cause);
        this.constraintViolationException = cause;
    }

    public ConstraintViolationException getConstraintViolationException() {
        return constraintViolationException;
    }
}
