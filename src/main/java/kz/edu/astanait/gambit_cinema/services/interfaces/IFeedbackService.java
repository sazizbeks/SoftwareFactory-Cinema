package kz.edu.astanait.gambit_cinema.services.interfaces;

import kz.edu.astanait.gambit_cinema.dto.FeedbackDto;
import kz.edu.astanait.gambit_cinema.models.Feedback;
import kz.edu.astanait.gambit_cinema.models.Movie;

import java.util.List;

public interface IFeedbackService {
    void add(Feedback feedback);
    List<Feedback> getFeedbacks(Movie movie);
    List<FeedbackDto> getFeedbackDtos(Movie movie);
}
