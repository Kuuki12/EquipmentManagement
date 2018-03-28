package pl.inzynierka.User.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.inzynierka.User.Repository.UserRepository;
import pl.inzynierka.User.Domain.User;

@Service
public class UserServiceCommand {

	UserRepository userRepository;

	public UserServiceCommand(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void createUser(User user){
		userRepository.save(user);
	}
	
	public void changeUserSettings(User user){
		userRepository.save(user);
	}
	
}
