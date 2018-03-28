package pl.inzynierka.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ContentTypeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentTypeConstraint {
	String message() default "Nie takie rozszerzenie";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String[] value();
}
