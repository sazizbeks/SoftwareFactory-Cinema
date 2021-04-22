package kz.edu.astanait.gambit_cinema.services;

import kz.edu.astanait.gambit_cinema.dto.FeedbackDto;
import kz.edu.astanait.gambit_cinema.dto.UserDto;
import kz.edu.astanait.gambit_cinema.models.Feedback;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.repositories.FeedbackRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IFeedbackService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void add(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbacks(Movie movie) {
        return feedbackRepository.findFeedbacksByMovie(movie);
    }

    @Override
    public List<FeedbackDto> getFeedbackDtos(Movie movie) {
        return feedbackRepository.findFeedbacksByMovie(movie).stream()
                .map(feedback -> FeedbackDto.builder()
                        .id(feedback.getId())
                        .user(UserDto.builder()
                                .id(feedback.getUser().getId())
                                .username(feedback.getUser().getUsername())
                                .build())
                        .comment(feedback.getComment())
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
