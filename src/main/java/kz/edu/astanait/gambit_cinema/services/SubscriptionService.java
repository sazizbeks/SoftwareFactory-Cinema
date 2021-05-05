package kz.edu.astanait.gambit_cinema.services;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.repositories.SubscriptionRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@AllArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void buy(User user, int month) {
        subscriptionRepository.save(Subscription.builder()
                .user(user)
                .expirationDate(getNewExpirationDate(month))
                .build());
    }

    @Override
    public Calendar checkExpirationDate(User user) throws NotFoundException {
        return getSubscription(user).getExpirationDate();
    }

    @Transactional
    @Override
    public void cancel(User user) throws NotFoundException {
        Subscription subscription = getSubscription(user);
        subscriptionRepository.delete(subscription);
    }

    @Override
    public Subscription getSubscription(User user) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findByUser(user);
        if (subscription == null) throw new NotFoundException("You have not a subscription.");
        return subscription;
    }

    @Override
    public void updateSubscriptionPlan(User user, int month) throws NotFoundException {
        Subscription subscription = getSubscription(user);
        subscription.getExpirationDate().add(Calendar.MONTH, month);
        subscriptionRepository.save(subscription);
    }

    private Calendar getNewExpirationDate(int month) {
        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.MONTH, month);
        return expDate;
    }
}
