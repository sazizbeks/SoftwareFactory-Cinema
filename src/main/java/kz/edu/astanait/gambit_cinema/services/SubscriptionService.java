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
    public Subscription buy(Long userId, int month) {
        Subscription subscription = Subscription.builder()
                .user(User.builder().id(userId).build())
                .expirationDate(getNewExpirationDate(month))
                .build();
        subscriptionRepository.save(subscription);
        return subscription;
    }

    @Override
    public Long checkExpirationDate(Long userId) throws NotFoundException {
        return getSubscription(userId).getExpirationDate().getTimeInMillis();
    }

    @Transactional
    @Override
    public void cancel(Long userId) throws NotFoundException {
        Subscription subscription = getSubscription(userId);
        subscriptionRepository.delete(subscription);
    }

    @Override
    public Subscription getSubscription(Long userId) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findSubscriptionByUser(User.builder().id(userId).build());
        if (subscription == null) throw new NotFoundException("You have not a subscription.");
        return subscription;
    }

    @Override
    public Subscription updateSubscriptionPlan(Long userId, int month) throws NotFoundException {
        Subscription subscription = getSubscription(userId);
        subscription.getExpirationDate().add(Calendar.MONTH, month);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    private Calendar getNewExpirationDate(int month) {
        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.MONTH, month);
        return expDate;
    }
}
