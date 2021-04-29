package kz.edu.astanait.gambit_cinema.services;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.MoviePageDto;
import kz.edu.astanait.gambit_cinema.dto.favoritelist.FavoriteMovieIdsDto;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.repositories.MovieRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IFeedbackService;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final IFeedbackService feedbackService;
    private final Random random;

    @Autowired
    public MovieService(MovieRepository movieRepository, IFeedbackService feedbackService) {
        this.movieRepository = movieRepository;
        this.feedbackService = feedbackService;
        this.random = new Random();
    }

    @Override
    public MoviePageDto getMoviePageDtoById(Long movieId, Long userId) throws BadRequestException {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            return MoviePageDto.builder()
                    .movie(movie)
                    .feedbacks(feedbackService.getFeedbackDtos(movie))
                    .isFavorite(movieRepository.favoriteMovieExists(userId, movieId))
                    .build();
        }
        throw new BadRequestException("No such ID");
    }

    @Override
    public Movie getMovieById(Long id) throws BadRequestException {
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

    @Override
    public List<Movie> searchMovies(String searchInput) {
        return movieRepository.findMoviesByNameContaining(searchInput);
    }

    @Transactional
    @Override
    public boolean addOrDeleteMovieInFavoriteList(Long userId, Long movieId) {
        boolean exists = movieRepository.favoriteMovieExists(userId, movieId);
        if (exists) {
            movieRepository.deleteMovieFromFavoriteList(userId, movieId);
        } else {
            movieRepository.addMovieToFavoriteList(userId, movieId);
        }
        return !exists;
    }

    @Override
    public void saveFavoriteMoviesToDb(List<FavoriteMovieIdsDto> dtoList) {
        dtoList.forEach(dto -> {
            if (!movieRepository.favoriteMovieExists(dto.getUserId(), dto.getMovieId())) {
                movieRepository.addMovieToFavoriteList(dto.getUserId(), dto.getMovieId());
            }
        });
    }

    @Override
    public Set<Movie> getFavList(Long id) {
        return movieRepository.getFavList(id);
    }


}
