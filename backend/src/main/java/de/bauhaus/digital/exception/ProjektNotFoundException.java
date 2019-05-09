package de.bauhaus.digital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjektNotFoundException extends RuntimeException {
    public ProjektNotFoundException() {
        super();
    }

    public ProjektNotFoundException(String message) {
        super(message);
    }

    public ProjektNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
