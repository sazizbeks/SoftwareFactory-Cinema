package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.models.Feedback;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.models.User;
import kz.edu.astanait.gambit_cinema.services.interfaces.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final IFeedbackService feedbackService;

    @Autowired
    public FeedbackController(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/add")
    public void addFeedback(@RequestParam String comment, @RequestParam Long movieId, HttpServletRequest request) {
        feedbackService.add(Feedback.builder()
                .comment(comment)
                .movie(Movie.builder().id(movieId).build())
                .user((User) request.getSession().getAttribute("user"))
                .build());
    }
}
