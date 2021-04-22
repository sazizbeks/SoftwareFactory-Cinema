package kz.edu.astanait.gambit_cinema.dto;

import kz.edu.astanait.gambit_cinema.models.Movie;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MoviePageDto {
    private Movie movie;
    private List<FeedbackDto> feedbacks;
}
