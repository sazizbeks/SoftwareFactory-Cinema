package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private static final String ID_ERROR_MESSAGE = "Something went wrong";
    private static final String SPLASH_SCREEN_TEMPLATE = "splash-screen";
    private static final String MOVIE_TEMPLATE = "movie-page";

    private final IMovieService movieService;

    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        try {
            Movie movie = movieService.getById(id);
            model.addAttribute("movie", movie);
            return MOVIE_TEMPLATE;
        } catch (BadRequestException e) {
            model.addAttribute("message", e.getMessage());
            return SPLASH_SCREEN_TEMPLATE;
        }
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("movie")
                      @Validated(ValidationMarkers.OnCreate.class) Movie movie,
                      Model model) {
        try {
            movieService.add(movie);
            model.addAttribute("message", "Movie successfully added");
        } catch (BadRequestException e) {
            model.addAttribute("message", ID_ERROR_MESSAGE);
        }
        return SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("movie")
                       @Validated(ValidationMarkers.OnUpdate.class) Movie movie,
                       Model model) {
        try {
            movieService.edit(movie);
            model.addAttribute("message", "Movie successfully edited");
        } catch (BadRequestException e) {
            model.addAttribute("message", ID_ERROR_MESSAGE);
        }
        return SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        try {
            Movie movie = new Movie();
            movie.setId(id);
            movieService.delete(movie);
            model.addAttribute("message", "Movie successfully deleted");
        } catch (BadRequestException e) {
            model.addAttribute("message", ID_ERROR_MESSAGE);
        }
        return SPLASH_SCREEN_TEMPLATE;
    }
}
