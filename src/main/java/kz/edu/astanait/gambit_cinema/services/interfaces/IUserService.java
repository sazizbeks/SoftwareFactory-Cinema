package kz.edu.astanait.gambit_cinema.services.interfaces;

import kz.edu.astanait.gambit_cinema.exceptions.RegistrationException;
import kz.edu.astanait.gambit_cinema.models.User;

public interface IUserService {
    /**
     * Register new user
     * @param user user to register
     * @throws RegistrationException exception during registration.
     *      It's thrown when:
     *      1) username is null or empty
     *      2) user with user.username username is exists in database
     */
    void register(User user) throws RegistrationException;
}
