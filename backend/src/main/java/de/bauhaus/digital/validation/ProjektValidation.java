package de.bauhaus.digital.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ProjektValidator.class)
public @interface ProjektValidation {

    String message() default "{de.bauhaus.digital.validation.projektvalidation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
