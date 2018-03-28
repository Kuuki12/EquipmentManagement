package pl.inzynierka.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EquipmentDoesntExistValidator implements ConstraintValidator<EquipmentDoesntExistConstraint, Long>{

	EquipmentRepository equipmentRepository;

	@Autowired
	public EquipmentDoesntExistValidator(EquipmentRepository equipmentRepository) {
		this.equipmentRepository = equipmentRepository;
	}

	@Override
	public void initialize(EquipmentDoesntExistConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		Equipment equipment = equipmentRepository.findById(value);
		if(equipment == null){
			return false;
		}else {
			return true;
		}
	}
	
}
