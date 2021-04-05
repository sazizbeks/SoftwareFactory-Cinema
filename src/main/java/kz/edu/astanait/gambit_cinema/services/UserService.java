package kz.edu.astanait.gambit_cinema.services;

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

}
