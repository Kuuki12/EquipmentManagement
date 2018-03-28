package pl.inzynierka.User.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.UserDoesntExist;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

@Service
public class UserServiceQuery {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    public User getUserByEmail(String email){

        User user = userRepository.findByUsername(email);

        if(user == null) throw new UserDoesntExist();

        return user;
    }

    public Page<UsersListQuery> getAllUsers(Pageable pageable){
        return userRepository.findAllUsers(pageable);
    }

    public User getUserById(Long id){

        User user = userRepository.findOne(id);

        return user;
    }

    public UpdateUserQuery getUserToUpdateForm(Long id){
        return userRepository.findById(id);
    }

    public PersonalInformationQuery getPersonalInformation(){
        return userRepository.findByUsernameAndReturnPersonalInformationQuery(getUserEmail());
    }

    private String getUserEmail(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
