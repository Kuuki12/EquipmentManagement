package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class ChangePasswordHandler {

    @Autowired
    UserRepository userRepository;

    @CommandHandler
    public void handle(ChangePasswordCommand changePasswordCommand){
        User user = userRepository.findOne(changePasswordCommand.getId());

        user.changePassword(changePasswordCommand.getNewPassword());

        userRepository.save(user);
    }

}
