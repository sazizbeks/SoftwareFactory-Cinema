package kz.edu.astanait.gambit_cinema.services.interfaces;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.models.Subscription;

public interface ISubscriptionService {
    Subscription buy(Long userId, int month);

    Long checkExpirationDate(Long userId) throws NotFoundException;

    void cancel(Long userId) throws NotFoundException;

    Subscription getSubscription(Long userId) throws NotFoundException;

    Subscription updateSubscriptionPlan(Long userId, int month) throws NotFoundException;
}
