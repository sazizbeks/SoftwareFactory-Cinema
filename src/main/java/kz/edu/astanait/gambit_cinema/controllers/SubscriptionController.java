package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;
}
