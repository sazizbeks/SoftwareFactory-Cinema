package kz.edu.astanait.gambit_cinema.services;

import kz.edu.astanait.gambit_cinema.repositories.FeedbackRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
}
