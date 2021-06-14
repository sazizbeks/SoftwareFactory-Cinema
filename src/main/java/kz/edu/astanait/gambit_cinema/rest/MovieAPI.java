package kz.edu.astanait.gambit_cinema.rest;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.MoviePageDto;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import kz.edu.astanait.gambit_cinema.tools.ExceptionManager;
import kz.edu.astanait.gambit_cinema.tools.MoviePageDtoList;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * MovieAPI rest controller class
 * REST API class for working with movies
 */
@RestController
@RequestMapping("/api/movie")
public class MovieAPI {
    private final IMovieService movieService;

    @Autowired
    public MovieAPI(IMovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * /api/movie/{movieId}?u={userId}
     * Gets movie by movie id
     * @param movieId id of movie
     * @param userId id of user to identify if a movie favorite for user
     * @return response. movie object if OK
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable Long movieId, @RequestParam("u") Long userId) {
        try {
            MoviePageDto movie = movieService.getMoviePageDtoById(movieId, userId);
            return ResponseEntity
                    .ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /api/movie/get-all
     * Gets all movies
     * @param userId id of user to identify if a movie favorite for user
     * @return List of movie
     */
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam("u") Long userId) {
        List<MoviePageDto> movies = movieService.getAllMoviePageDto(userId);
        return ResponseEntity
                .ok(new MoviePageDtoList(movies));
    }

    /**
     * /api/movie/random
     * Gets random movie based on genres that client selected
     * @param genres selected genres
     * @return random movie
     */
    @GetMapping("/random")
    public ResponseEntity<?> count(@RequestBody Set<Genre> genres) {
        try {
            return ResponseEntity.ok(movieService.getRandomMovie(genres));
        } catch (NotFoundException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.NOT_FOUND, e);
        }
    }

    /**
     * /api/movie/add
     * @param movie movie to add
     * @return movie
     */
    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody @Validated({ValidationMarkers.OnCreate.class,
            ValidationMarkers.APIOnCreate.class}) Movie movie,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, bindingResult);
        }
        try {
            movieService.add(movie);
            return ResponseEntity.ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /api/movie/edit
     * Edits movie
     * @param movie movie to be edited
     * @return movie
     */
    @PutMapping("/edit")
    public ResponseEntity<?> editMovie(@RequestBody @Validated({ValidationMarkers.OnUpdate.class,
            ValidationMarkers.APIOnUpdate.class}) Movie movie,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, bindingResult);
        }

        try {
            movieService.edit(movie);
            return ResponseEntity.ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /api/movie/delete
     * Deletes movie
     * @param movie movie to be deleted
     * @return response
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMovie(@RequestBody Movie movie) {
        try {
            movieService.delete(movie);
            return ResponseEntity.ok("Movie successfully deleted");
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }
}
