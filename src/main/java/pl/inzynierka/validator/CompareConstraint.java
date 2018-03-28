package pl.inzynierka.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = CompareValidator.class)
@Target( {ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareConstraint {
	String message() default "Values doesn't maches";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String firstValue();
	String secondValue();
}
