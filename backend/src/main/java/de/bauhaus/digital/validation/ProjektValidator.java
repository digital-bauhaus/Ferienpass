package de.bauhaus.digital.validation;

import de.bauhaus.digital.domain.Projekt;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjektValidator implements ConstraintValidator<ProjektValidation, Projekt> {

    @Override
    public void initialize(ProjektValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Projekt value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value.getDatumEnde() != null && value.getDatumEnde().isBefore(value.getDatumBeginn())) {
            context.buildConstraintViolationWithTemplate("Enddatum darf nicht vor Beginndatum liegen.").addPropertyNode("datumEnde").addConstraintViolation();
            valid = false;
        }
        if (value.getHoechstAlter() < value.getMindestAlter()) {
            context.buildConstraintViolationWithTemplate("Höchstalter darf nicht kleiner als das Mindestalter sein.").addPropertyNode("hoechstAlter").addConstraintViolation();
            valid = false;
        }
        if (value.getPlaetzeReserviert() > value.getPlaetzeGesamt()) {
            context.buildConstraintViolationWithTemplate("Es dürfen nicht mehr Plätze reserviert werden als überhaupt verfügbar sind.").addPropertyNode("plaetzeReserviert").addConstraintViolation();
            valid = false;
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }
}
