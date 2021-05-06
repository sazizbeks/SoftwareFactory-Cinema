package kz.edu.astanait.gambit_cinema.rest;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.SubscriptionDto;
import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionAPI {
    private final ISubscriptionService subscriptionService;

    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkExpirationDate(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(subscriptionService.checkExpirationDate(userId));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> buySubscription(@RequestBody SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionService.buy(subscriptionDto.getUserId(), subscriptionDto.getMonth());
        subscriptionDto.setExpirationDate(subscription.getExpirationDate());
        return ResponseEntity.ok(subscriptionDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        Subscription subscription;
        try {
            subscription = subscriptionService.updateSubscriptionPlan(subscriptionDto.getUserId(), subscriptionDto.getMonth());
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/cancel/{userId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable Long userId) {
        try {
            subscriptionService.cancel(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Your subscription will be canceled after expiration date.");
    }
}
