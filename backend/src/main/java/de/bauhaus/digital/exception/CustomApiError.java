package de.bauhaus.digital.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class CustomApiError {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public CustomApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "CustomApiError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
