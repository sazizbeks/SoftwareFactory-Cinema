package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SubscriptionAPI {
    private final ISubscriptionService subscriptionService;


}
