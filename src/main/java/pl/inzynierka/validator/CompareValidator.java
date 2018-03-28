package pl.inzynierka.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;


public class CompareValidator implements ConstraintValidator<CompareConstraint, Object>{

	String firstValue;
	String secondValue;
	
	@Override
	public void initialize(CompareConstraint constraintAnnotation) {
		firstValue = constraintAnnotation.firstValue();
		secondValue = constraintAnnotation.secondValue();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		try {
			Object firstValue = BeanUtils.getProperty(value, this.firstValue);
			Object secondValue = BeanUtils.getProperty(value, this.secondValue);
			
			return firstValue.equals(secondValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
