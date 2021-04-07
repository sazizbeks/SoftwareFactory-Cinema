package kz.edu.astanait.gambit_cinema.services;

import kz.edu.astanait.gambit_cinema.exceptions.BadCredentialsException;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.exceptions.RegistrationException;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.repositories.RoleRepository;
import kz.edu.astanait.gambit_cinema.repositories.UserRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(User user) throws RegistrationException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new RegistrationException("Username is empty");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RegistrationException("User with such username exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("USER"));

        userRepository.save(user);
    }

    @Override
    public User validateAndReturnUserOrThrowException(String username, String password) throws BadCredentialsException, BadRequestException {
        if (username.isEmpty()) {
            throw new BadRequestException("Username is empty");
        }
        if (password.isEmpty()) {
            throw new BadRequestException("Password is empty");
        }

        User user = userRepository.findByUsername(username);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new BadCredentialsException("Password is not correct");
            }
        } else {
            throw new BadRequestException("No such user with this username");
        }
    }

}
