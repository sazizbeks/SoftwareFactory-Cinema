package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.ValidationMarkers;
import kz.edu.astanait.gambit_cinema.exceptions.BadCredentialsException;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.exceptions.PasswordConfirmationException;
import kz.edu.astanait.gambit_cinema.exceptions.UserExistsException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    private final IUserService userService;

    public UserAPI(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/registration", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody @Validated(ValidationMarkers.OnRegistration.class) User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok(user);
        } catch (UserExistsException | PasswordConfirmationException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody @Validated(ValidationMarkers.OnLogin.class) User user) {
        try {
            User validatedUser = userService.validateAndReturnUserOrThrowException(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(validatedUser);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
