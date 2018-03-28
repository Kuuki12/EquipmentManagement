package pl.inzynierka.User.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.inzynierka.User.Command.AddUserCommand;
import pl.inzynierka.User.Command.RegistryUserCommand;
import pl.inzynierka.User.Command.UserServiceCommand;

import javax.validation.Valid;

@Controller
public class SignUpController {
	
	UserServiceCommand userService;
	CommandGateway commandGateway;

	@Autowired
	public SignUpController(CommandGateway commandGateway, UserServiceCommand userService){
		this.userService = userService;
		this.commandGateway = commandGateway;
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public ModelAndView signUp(){
		
		ModelAndView mav = new ModelAndView("/signUp");
		mav.addObject("user", new RegistryUserCommand());
		
		return mav;
	}
	
	@RequestMapping(value = "/saveSignUp", method = RequestMethod.POST)
	public String saveSignUp(@Valid @ModelAttribute("user") RegistryUserCommand user, BindingResult result){

		if(result.hasErrors()){
			return "/signUp";
		}

		commandGateway.send(user);
		
		return "redirect:/signIn";
	}
	
}
