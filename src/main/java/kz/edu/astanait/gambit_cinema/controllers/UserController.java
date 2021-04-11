package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.ValidationMarkers;
import kz.edu.astanait.gambit_cinema.exceptions.PasswordConfirmationException;
import kz.edu.astanait.gambit_cinema.exceptions.UserExistsException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;

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
    public String register(@ModelAttribute("userForm") @Validated(ValidationMarkers.OnRegistration.class) User user,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            user.setBirthDate(new Date(2002, Calendar.APRIL,29));
            userService.register(user);
        } catch (UserExistsException e) {
            model.addAttribute("userExistsError", e.getMessage());
            return "registration";
        } catch (PasswordConfirmationException e) {
            model.addAttribute("rePasswordError", e.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }
}
