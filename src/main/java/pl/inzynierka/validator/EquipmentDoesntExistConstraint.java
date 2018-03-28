package pl.inzynierka.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EquipmentDoesntExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EquipmentDoesntExistConstraint {
	String message() default "Sprzęt nie istnieje";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
