package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.exceptions.BirthDateException;
import kz.edu.astanait.gambit_cinema.exceptions.PasswordConfirmationException;
import kz.edu.astanait.gambit_cinema.exceptions.UserExistsException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView mav = new ModelAndView("registration");
        mav.addObject("userForm", new User());
        return mav;
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("userForm") @Validated(ValidationMarkers.OnRegistration.class) User user,
                           BindingResult bindingResult, Model model, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        String birthDateString = request.getParameter("birth-date");

        try {
            Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
            user.setBirthDate(birthDate);

            userService.register(user);
        } catch (UserExistsException e) {
            model.addAttribute("userExistsError", e.getMessage());
            return "registration";
        } catch (PasswordConfirmationException e) {
            model.addAttribute("rePasswordError", e.getMessage());
            return "registration";
        } catch (BirthDateException | ParseException e) {
            model.addAttribute("birthDateError", e.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }
}
