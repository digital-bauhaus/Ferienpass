package de.bauhaus.digital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationInvalidException extends RuntimeException {
    public RegistrationInvalidException() {
        super();
    }

    public RegistrationInvalidException(String message) {
        super(message);
    }

    public RegistrationInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
