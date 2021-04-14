package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import kz.edu.astanait.gambit_cinema.tools.ExceptionManager;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieAPI {
    private final IMovieService movieService;

    @Autowired
    public MovieAPI(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getMovieById(@PathVariable Long id){
        try {
            Movie movie = movieService.getById(id);
            return ResponseEntity
                    .ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody @Validated(ValidationMarkers.OnCreate.class) Movie movie,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,bindingResult);
        }
        try {
            movieService.add(movie);
            return ResponseEntity.ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,e);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editMovie(@RequestBody @Validated(ValidationMarkers.OnUpdate.class) Movie movie,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,bindingResult);
        }

        try {
            movieService.edit(movie);
            return ResponseEntity.ok(movie);
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMovie(@RequestBody Movie movie) {
        try {
            movieService.delete(movie);
            return ResponseEntity.ok("Movie successfully deleted");
        } catch (BadRequestException e) {
            return ExceptionManager.getResponseEntity(HttpStatus.BAD_REQUEST,e);
        }
    }
}
