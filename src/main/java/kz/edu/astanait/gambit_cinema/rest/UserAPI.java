package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.exceptions.RegistrationException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path = "/registration",
            consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok(user);
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}
