package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.exceptions.RegistrationException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView mav = new ModelAndView("registration");
        mav.addObject("userForm", new User());
        return mav;
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("userForm") User user) {
        try {
            userService.register(user);
        } catch (RegistrationException e) {
            e.printStackTrace();
        }
        return "login";
    }
}
