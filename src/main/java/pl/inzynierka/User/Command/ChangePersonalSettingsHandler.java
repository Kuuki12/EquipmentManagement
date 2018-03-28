package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class ChangePersonalSettingsHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceCommand userServiceCommand;

    @CommandHandler
    public void handle(ChangePersonalSettingsCommand changePersonalSettingsCommand){

        User user = userRepository.findByUsername(getUserEmail());

        user.changePersonalSettings(changePersonalSettingsCommand.getFirstName(), changePersonalSettingsCommand.getLastName());

        userServiceCommand.changeUserSettings(user);
    }

    private String getUserEmail(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
