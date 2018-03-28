package pl.inzynierka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {

    @RequestMapping(value = "/errors/403", method = RequestMethod.GET)
    public ModelAndView error403(){

        ModelAndView modelAndView = new ModelAndView("403");

        return modelAndView;
    }

}
