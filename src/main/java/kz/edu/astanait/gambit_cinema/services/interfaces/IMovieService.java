package kz.edu.astanait.gambit_cinema.services.interfaces;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.MoviePageDto;
import kz.edu.astanait.gambit_cinema.dto.favoritelist.FavoriteMovieIdsDto;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface IMovieService {
    void add(Movie movie) throws BadRequestException;
    void edit(Movie movie) throws BadRequestException;
    void delete(Movie movie) throws BadRequestException;
    MoviePageDto getMoviePageDtoById(Long movieId, Long userId) throws BadRequestException;

    List<MoviePageDto> getAllMoviePageDto(Long userId);

    Movie getMovieById(Long id) throws BadRequestException;
    Movie getRandomMovie(Set<Genre> genres) throws NotFoundException;
    List<Movie> searchMovies(String searchInput);
    boolean addOrDeleteMovieInFavoriteList(Long userId, Long movieId);

    @Transactional
    void saveFavoriteMoviesToDb(List<FavoriteMovieIdsDto> dtoList);

    Set<Movie> getFavList(Long id);
}
