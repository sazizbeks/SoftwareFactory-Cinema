package kz.edu.astanait.gambit_cinema.services.interfaces;

import kz.edu.astanait.gambit_cinema.exceptions.BadCredentialsException;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.exceptions.RegistrationException;
import kz.edu.astanait.gambit_cinema.models.User;

public interface IUserService {
    /**
     * Register new user
     *
     * @param user user to register
     * @throws RegistrationException exception during registration.
     *                               It's thrown when:
     *                               1) username is null or empty
     *                               2) user with user.username username is exists in database
     */
    void register(User user) throws RegistrationException;

    /**
     * @param username username
     * @param password password mapped to username
     * @return User if username and password are mapped
     * @throws BadCredentialsException if username and password are not mapped
     * @throws BadRequestException     if:
     *                                 1) username or password is empty
     *                                 2) username not found
     */
    User validateAndReturnUserOrThrowException(String username, String password) throws BadCredentialsException, BadRequestException;
}
