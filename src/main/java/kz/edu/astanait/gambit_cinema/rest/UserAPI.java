package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.exceptions.*;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import kz.edu.astanait.gambit_cinema.tools.ExceptionManager;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    private final IUserService userService;

    public UserAPI(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/registration", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody @Validated(ValidationMarkers.OnRegistration.class) User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,bindingResult);
        }
        try {
            userService.register(user);
            return ResponseEntity.ok(user);
        } catch (UserExistsException | PasswordConfirmationException | BirthDateException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,e);
        }
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody @Validated(ValidationMarkers.OnLogin.class) User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,bindingResult);
        }
        try {
            User validatedUser = userService.validateAndReturnUserOrThrowException(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(validatedUser);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,e);
        } catch (BadCredentialsException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.UNAUTHORIZED,e);
        }
    }
}
