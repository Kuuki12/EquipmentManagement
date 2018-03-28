package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Exceptions.UserAlreadyExists;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class RegistryUserHandler {

    @Autowired
    UserServiceCommand userService;

    @Autowired
    UserRepository userRepository;

    @CommandHandler
    public void handle(RegistryUserCommand registryUserCommand){

        validUser(registryUserCommand.getEmail());

        User user = new User(registryUserCommand.getFirstName(), registryUserCommand.getLastName(), registryUserCommand.getEmail(), registryUserCommand.getPassword());

        userService.createUser(user);
    }

    private User validUser(String email){
        User user = userRepository.findByUsername(email);

        if(user != null) throw new UserAlreadyExists();

        return user;
    }

}
