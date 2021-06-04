package kz.edu.astanait.gambit_cinema.controllers;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.models.Subscription;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.ISubscriptionService;
import kz.edu.astanait.gambit_cinema.tools.StaticValues;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@AllArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

    @GetMapping("")
    public String showSubscription(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");

        try {
            model.addAttribute("subscription",subscriptionService.getSubscription(user.getId()));
        } catch (NotFoundException e) {
            model.addAttribute("subscription", null);
        }
        return "subscription";
    }

    @GetMapping("/check")
    public String checkExpDate(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        try {
            Long expDateInMills = subscriptionService.checkExpirationDate(user.getId());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(expDateInMills);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            model.addAttribute("message", sdf.format(calendar));
        } catch (NotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/subscribe")
    public String buySubscription(@RequestParam("month") Integer month, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        subscriptionService.buy(user.getId(), month);
        model.addAttribute("message", "You have successfully subscribed for " + month + " months");

        user.setSubscribed(true);
        session.setAttribute("user",user);
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/update")
    public String updateSubscription(Integer month, HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        try {
            Subscription subscription = subscriptionService.updateSubscriptionPlan(user.getId(), month);
            model.addAttribute("message", "You have successfully updated your subscription for " + month + " months");

        } catch (NotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/cancel")
    public String cancelSubscription(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        try {
            subscriptionService.cancel(user.getId());
            model.addAttribute("message", "You have successfully canceled your subscription.");

            user.setSubscribed(false);
            session.setAttribute("user",user);
        } catch (NotFoundException e) {
            model.addAttribute("message", "Something went wrong.");
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }
}

