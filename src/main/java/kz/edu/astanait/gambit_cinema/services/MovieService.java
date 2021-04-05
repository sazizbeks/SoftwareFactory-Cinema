package kz.edu.astanait.gambit_cinema.services;

import kz.edu.astanait.gambit_cinema.repositories.MovieRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import org.springframework.stereotype.Service;

@Service
public class MovieService implements IMovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
