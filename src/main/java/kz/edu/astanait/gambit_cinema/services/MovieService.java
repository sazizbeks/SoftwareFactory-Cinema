package kz.edu.astanait.gambit_cinema.services;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.repositories.MovieRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final Random random;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.random = new Random();
    }

    @Override
    public Movie getById(Long id) throws BadRequestException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return optionalMovie.get();
        }
        throw new BadRequestException("No such ID");
    }

    @Override
    public Movie getRandomMovie(Set<Genre> genres) throws NotFoundException {
        List<Movie> movies = movieRepository.getDistinctByGenresIn(genres);
        int count = movies.size();
        if (count != 0) {
            return movies.get(random.nextInt(count));
        } else {
            throw new NotFoundException("Movies not found");
        }
    }

    @Override
    public void add(Movie movie) throws BadRequestException {
        if (movie.getId() != null) {
            throw new BadRequestException("Movie ID should not be specified");
        }
        movieRepository.save(movie);
    }

    @Override
    public void edit(Movie movie) throws BadRequestException {
        movieIdNotExist(movie.getId());
        movieRepository.save(movie);
    }

    @Override
    public void delete(Movie movie) throws BadRequestException {
        movieIdNotExist(movie.getId());
        movieRepository.delete(movie);
    }

    private void movieIdNotExist(Long id) throws BadRequestException {
        if (!movieRepository.existsById(id))
            throw new BadRequestException("Movie with such ID does not exist");
    }
}
