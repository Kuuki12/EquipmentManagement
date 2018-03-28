package pl.inzynierka.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ContentTypeValidator implements ConstraintValidator<ContentTypeConstraint, MultipartFile>{

	String[] acceptedContentTypes;
	
	@Override
	public void initialize(ContentTypeConstraint constraintAnnotation) {
		this.acceptedContentTypes = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return acceptContentType(value.getContentType(), acceptedContentTypes);
	}
	
	private static boolean acceptContentType(String contentType, String[] acceptedContentTypes) {
	    for (String accept : acceptedContentTypes) {
	        if (contentType.equalsIgnoreCase(accept)) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
