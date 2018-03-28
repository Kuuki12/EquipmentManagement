package pl.inzynierka.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class NotFileValidator implements ConstraintValidator<NotFileConstraint, MultipartFile>{

	@Override
	public void initialize(NotFileConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if(value.isEmpty() == true){
			return false;
		}else{
			return true;
		}
	}
	
}
