package kz.edu.astanait.gambit_cinema.dto;

import kz.edu.astanait.gambit_cinema.models.User;
import lombok.Data;

@Data
public class RegistrationWithSubscriptionDto {
    private User user;
    private SubscriptionDto subscriptionDto;
}
