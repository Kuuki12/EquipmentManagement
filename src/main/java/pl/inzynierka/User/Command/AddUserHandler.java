package pl.inzynierka.User.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Exceptions.UserAlreadyExists;
import pl.inzynierka.Repairman.Domain.Employee;
import pl.inzynierka.Repairman.Repository.RepairmanRepository;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Component
public class AddUserHandler {

    @Autowired
    UserServiceCommand userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RepairmanRepository repairmanRepository;

    @Autowired
    ClientRepository clientRepository;

    @CommandHandler
    public void handle(AddUserCommand addUserCommand){

        validUser(addUserCommand.getEmail());

        User user = new User(addUserCommand.getFirstName(), addUserCommand.getLastName(), addUserCommand.getEmail(), addUserCommand.getPassword(), addUserCommand.getRole());

        userService.createUser(user);

        if(user.getRole() == Role.ROLE_CLIENT) clientRepository.save(new Client(user, null));
        if(addUserCommand.getRole() == Role.ROLE_REPAIRMAN || addUserCommand.getRole() == Role.ROLE_ADMIN) {
            Employee employee = new Employee(user);
            repairmanRepository.save(employee);
        }
    }

    private User validUser(String email){
        User user = userRepository.findByUsername(email);

        if(user != null) throw new UserAlreadyExists();

        return user;
    }
}
