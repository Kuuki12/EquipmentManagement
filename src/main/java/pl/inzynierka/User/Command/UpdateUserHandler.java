package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class UpdateUserHandler {

    @Autowired
    UserRepository userRepository;

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand){

        User user = userRepository.findOne(updateUserCommand.getId());

        user.changePersonalSettings(updateUserCommand.getFirstName(), updateUserCommand.getLastName());
        user.changeRole(updateUserCommand.getRole());

        userRepository.save(user);
    }

}
