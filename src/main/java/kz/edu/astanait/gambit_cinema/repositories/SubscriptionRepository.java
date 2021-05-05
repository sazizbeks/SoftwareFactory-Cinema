package kz.edu.astanait.gambit_cinema.repositories;

import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByUser(User user);
}
