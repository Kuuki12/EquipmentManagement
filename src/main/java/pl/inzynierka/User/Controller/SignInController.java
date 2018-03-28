package pl.inzynierka.User.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignInController {

    @Autowired
    CommandGateway commandGateway;

	@RequestMapping("/login")
	public ModelAndView signIn(){
		ModelAndView mav = new ModelAndView("/login");

		return mav;
	}

	@RequestMapping("/udaloSie")
	public ModelAndView udalosie(){
		ModelAndView mav = new ModelAndView("/udalosie");

		return mav;
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public ModelAndView signIn(@RequestParam(value = "error",required = false) String error,
			@RequestParam(value = "logout",required = false) String logout,
			@RequestParam(value = "expired",required = false) String expired) {

		ModelAndView model = new ModelAndView("/signIn");

		if (error != null) {
			model.addObject("info", "Blad logowania");
		}

		if (logout != null) {
			model.addObject("info", "Wylogowano pomyslnie");
		}

		if (expired != null) {
			model.addObject("info", "Twoja sesja wygasła ponieważ ktoś się zalogował na twoje konto");
		}

		return model;
	}


}
