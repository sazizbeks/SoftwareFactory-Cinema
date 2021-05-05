package kz.edu.astanait.gambit_cinema.services.interfaces;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.models.User;

import java.util.Calendar;

public interface ISubscriptionService {
    void buy(User user, int month);

    Calendar checkExpirationDate(User user) throws NotFoundException;

    void cancel(User user) throws NotFoundException;

    Subscription getSubscription(User user) throws NotFoundException;

    void updateSubscriptionPlan(User user, int month) throws NotFoundException;
}
