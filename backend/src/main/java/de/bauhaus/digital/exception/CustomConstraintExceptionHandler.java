package de.bauhaus.digital.exception;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomConstraintExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomApiError handleCustomConstraintException(CustomConstraintException exception,
            ServletWebRequest webRequest) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolationException().getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        CustomApiError customApiError = new CustomApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), errors);
        System.out.println(customApiError);

        //return new ResponseEntity<>(customApiError, new HttpHeaders(), customApiError.getStatus());
        return customApiError;
    }

}
