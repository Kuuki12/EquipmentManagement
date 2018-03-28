package pl.inzynierka.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pl.inzynierka.User.Repository.UserRepository;
import pl.inzynierka.User.Domain.User;

public class UserNotExistsValidator implements ConstraintValidator<UserNotExistsConstraint, String>{

    UserRepository userDAO;

    @Autowired
	public UserNotExistsValidator(UserRepository userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void initialize(UserNotExistsConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(userDAO.findByUsername(value) == null){
			return false;
		}else{
			return true;
		}
	}
	
}
