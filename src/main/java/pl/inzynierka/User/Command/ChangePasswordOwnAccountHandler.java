package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class ChangePasswordOwnAccountHandler {

    @Autowired
    UserRepository userRepository;

    @CommandHandler
    public void handle(ChangePasswordOwnAccountCommand changePasswordOwnAccountCommand){
        User user = userRepository.findByUsername(changePasswordOwnAccountCommand.getUsername());

        user.changePassword(changePasswordOwnAccountCommand.getNewPassword());

        userRepository.save(user);
    }

}
