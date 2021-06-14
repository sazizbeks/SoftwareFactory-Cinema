package kz.edu.astanait.gambit_cinema.services.interfaces;

import kz.edu.astanait.gambit_cinema.dto.FeedbackDto;
import kz.edu.astanait.gambit_cinema.models.Feedback;
import kz.edu.astanait.gambit_cinema.models.Movie;

import java.util.List;

public interface IFeedbackService {
    /**
     * Adds new feedback
     * @param feedback feedback to add
     */
    void add(Feedback feedback);

    /**
     * Get feedbacks of specific movie
     * @param movie the movie from which the reviews are taken
     * @return
     */
    List<Feedback> getFeedbacks(Movie movie);
    List<FeedbackDto> getFeedbackDtos(Movie movie);
}
