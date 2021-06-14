package kz.edu.astanait.gambit_cinema.rest;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.SubscriptionDto;
import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SubscriptionAPI rest controller class
 * REST API class for working with subscription
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionAPI {
    private final ISubscriptionService subscriptionService;

    @Data
    @AllArgsConstructor
    private class ExpDateHolder{
        Long data;
    }

    /**
     * /api/subscription/check/{userId}
     * Checks expiration date of subscription of user
     * @param userId id of user to check expiration date of subscription
     * @return ExpDateHolder which has time in mills
     */
    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkExpirationDate(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(new ExpDateHolder(subscriptionService.checkExpirationDate(userId)));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * /api/subscription/subscribe
     * Buy subscription to user
     * @param subscriptionDto dto which holds userId, month, expirationDate
     * @return SubscriptionDto with setted expiration date
     */
    @PostMapping("/subscribe")
    public ResponseEntity<?> buySubscription(@RequestBody SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionService.buy(subscriptionDto.getUserId(), subscriptionDto.getMonth());
        subscriptionDto.setExpirationDate(subscription.getExpirationDate());
        return ResponseEntity.ok(subscriptionDto);
    }

    /**
     * /api/subscription/update
     * Updates subscription of user
     * @param subscriptionDto dto which holds userId, month, expirationDate
     * @return updated subscription
     */
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

    /**
     * /api/subscription/cancel/{userId}
     * Cancels subscription of user
     * @param userId user id to cancel subscription
     * @return response
     */
    @DeleteMapping("/cancel/{userId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable Long userId) {
        try {
            subscriptionService.cancel(userId);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Your subscription is canceled.");
    }
}
