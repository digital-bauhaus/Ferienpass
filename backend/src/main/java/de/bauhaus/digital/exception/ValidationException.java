package de.bauhaus.digital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Note: We use UNPROCESSABLE_ENTITY instead of BAD_REQUEST because
// Spring uses BAD_REQUEST for Validation and Json-Deserializing errors etc.
// So by using this error code, we can show the user the error-message in the frontend
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
