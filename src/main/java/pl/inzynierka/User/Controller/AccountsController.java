package pl.inzynierka.User.Controller;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.inzynierka.Exceptions.UserDoesntExist;
import pl.inzynierka.Pagination;
import pl.inzynierka.User.Command.*;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Query.UserServiceQuery;
import pl.inzynierka.User.Query.UsersListQuery;
import pl.inzynierka.User.Repository.UserRepository;

@Controller
public class AccountsController {

    UserServiceCommand userService;
	CommandGateway commandGateway;
	UserServiceQuery userServiceQuery;
	UserRepository userRepository;

	@Autowired
	public AccountsController(UserServiceCommand userService, CommandGateway commandGateway, UserServiceQuery userServiceQuery, UserRepository userRepository) {
		this.userService = userService;
		this.commandGateway = commandGateway;
		this.userServiceQuery = userServiceQuery;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/account/settings", method = RequestMethod.GET)
	public ModelAndView showAccountSettingForm(){
		ModelAndView mav = new ModelAndView("/account/setting");
		mav.addObject("personalInformation", userServiceQuery.getPersonalInformation());
		return mav;
	}
	
	@RequestMapping(value = "/account/personalInformation", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity changePersonalInformation(@RequestBody ChangePersonalSettingsCommand changePersonalSettingsCommand){
		return commandGateway.send(changePersonalSettingsCommand).isDone() ? new ResponseEntity("Dane personalne zostały zmienione", HttpStatus.OK) : new ResponseEntity("Wystąpil blad", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/account/password", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity changePasswordUser(@RequestBody ChangePasswordOwnAccountCommand changePasswordOwnAccountCommand) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		changePasswordOwnAccountCommand.setUsername(username);

		return commandGateway.send(changePasswordOwnAccountCommand).isDone() ? new ResponseEntity("Hasło zostało zmienione.",HttpStatus.OK) : new ResponseEntity("Hasło nie zostało zmienione.",HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView listUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
								  @RequestParam(required = false, defaultValue = "20") Integer size){

		ModelAndView mav = new ModelAndView("/user/list");

		Page<UsersListQuery> users = userServiceQuery.getAllUsers(new PageRequest(page,size, Sort.Direction.DESC, "dateCreation"));

		Pagination pagination =  (users != null) ? new Pagination(users, "/users") : null;

		mav.addObject("page", pagination);
		mav.addObject("users", users);

		return mav;
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.GET)
	public ModelAndView showCreateUserForm(){

		ModelAndView mav = new ModelAndView("/user/create");

		mav.addObject("user", new AddUserCommand());

		return mav;
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public String createUser(@ModelAttribute("user") @Valid AddUserCommand AddUserCommand, BindingResult result){

		if (result.hasErrors()) {
			return "/user/create";
		}

		commandGateway.send(AddUserCommand);

		return "redirect:/users";
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteUser(@PathVariable("id") Long id){

		User user = userRepository.findOne(id);

		if(user == null)throw new UserDoesntExist();

		userRepository.delete(id);
	}

	@RequestMapping(value = "/accounts/{id}/password", method = RequestMethod.GET)
	public ModelAndView showChangePasswordForm(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/account/changePassword");

		mav.addObject("account", new ChangePasswordCommand(id));

		return mav;
	}

	@RequestMapping(value = "/accounts/{id}/password", method = RequestMethod.POST)
	public String changePassword(
			@PathVariable("id") Long id,
			@ModelAttribute("account") @Valid ChangePasswordCommand changePasswordCommand, BindingResult result){

		changePasswordCommand.setId(id);

		if(result.hasErrors()) return "/account/changePassword";

		commandGateway.send(changePasswordCommand);

		return "redirect:/users";
	}

	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	public ModelAndView showAccountDetails(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/account/selected");

		mav.addObject("user", userServiceQuery.getUserById(id));

		return mav;
	}

	@RequestMapping(value = "/accounts/{id}/status/{status}", method = RequestMethod.GET)
	public String banAccount(@PathVariable("id") Long id, @PathVariable("status") Boolean status){

		User user = userRepository.findOne(id);
		user.changeStatus(status);
		userRepository.save(user);

		return "redirect:/accounts/"+id;
	}

	@RequestMapping(value = "/accounts/{id}/update", method = RequestMethod.GET)
	public ModelAndView showAccountUpdate(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/account/edit");

		mav.addObject("user", userServiceQuery.getUserToUpdateForm(id));

		return mav;
	}

	@RequestMapping(value = "/accounts/{id}/update", method = RequestMethod.POST)
	public String accountUpdate(@PathVariable("id") Long id, @ModelAttribute("user") @Valid UpdateUserCommand updateUserCommand, BindingResult result){

		if(result.hasErrors()) return "/account/edit";

		commandGateway.send(updateUserCommand);

		return "redirect:/users";
	}


}
