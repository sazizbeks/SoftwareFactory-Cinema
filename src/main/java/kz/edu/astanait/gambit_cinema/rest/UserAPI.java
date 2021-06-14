package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.dto.RegistrationWithSubscriptionDto;
import kz.edu.astanait.gambit_cinema.exceptions.*;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
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

/**
 * UserAPI rest controller class
 * REST API class for working with user
 */
@RestController
@RequestMapping("/api/user")
public class UserAPI {
    private final IUserService userService;
    private final ISubscriptionService subscriptionService;

    public UserAPI(IUserService userService, ISubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    /**
     * /api/user/registration
     * Registers new user
     * @param user user to be registered
     * @return user if OK
     */
    @PostMapping(path = "/registration", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody @Validated(ValidationMarkers.OnRegistration.class) User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, bindingResult);
        }
        try {
            userService.register(user);
            return ResponseEntity.ok(user);
        } catch (UserExistsException | PasswordConfirmationException | BirthDateException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /api/user/registration/with-subscription
     * Registers new user with subscription
     * @param registrationWithSubscriptionDto dto which holds user and subscription information
     * @return registered user
     */
    @PostMapping(path = "/registration/with-subscription", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody RegistrationWithSubscriptionDto registrationWithSubscriptionDto) {
        try {
            User user = userService.register(registrationWithSubscriptionDto.getUser());
            subscriptionService.buy(user.getId(), registrationWithSubscriptionDto.getSubscriptionDto().getMonth());
            return ResponseEntity.ok(user);
        } catch (UserExistsException | PasswordConfirmationException | BirthDateException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /api/user/login
     * Logins user to application
     * @param user user to login
     * @return validated user
     */
    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody @Validated(ValidationMarkers.OnLogin.class) User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, bindingResult);
        }
        try {
            User validatedUser = userService.validateAndReturnUserOrThrowException(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(validatedUser);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        } catch (BadCredentialsException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.UNAUTHORIZED, e);
        }
    }
}
