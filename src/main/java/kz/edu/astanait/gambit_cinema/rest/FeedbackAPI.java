package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.models.Feedback;
import kz.edu.astanait.gambit_cinema.services.interfaces.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FeedbackAPI rest controller class
 * REST API class for working with feedbacks
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackAPI {
    private final IFeedbackService feedbackService;

    @Autowired
    public FeedbackAPI(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * /api/feedback/add
     * Adds feedback to movie
     * @param feedback feedback to be added
     */
    @PostMapping("/add")
    public void addFeedback(@RequestBody Feedback feedback) {
        feedbackService.add(feedback);
    }
}
