package kz.edu.astanait.gambit_cinema.controllers;

import javassist.NotFoundException;
import kz.edu.astanait.gambit_cinema.dto.MoviePageDto;
import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.repositories.GenreRepository;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import kz.edu.astanait.gambit_cinema.tools.DateConverter;
import kz.edu.astanait.gambit_cinema.tools.StaticValues;
import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final IMovieService movieService;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieController(IMovieService movieService, GenreRepository genreRepository) {
        this.movieService = movieService;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model) {
        try {
            MoviePageDto movie = movieService.getById(id);
            model.addAttribute("movie", movie);
            return StaticValues.Templates.MOVIE_PAGE;
        } catch (BadRequestException e) {
            model.addAttribute("message", e.getMessage());
            return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
        }
    }

    private void addGenresToModel(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
    }

    @GetMapping("/add")
    public String showAddMoviePage(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("type", "add");
        addGenresToModel(model);
        return StaticValues.Templates.ADD_EDIT_MOVIE;
    }

    @GetMapping("/edit")
    public String showEditMoviePage(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("movie", movieService.getById(id));
            model.addAttribute("type", "edit");
            addGenresToModel(model);
        } catch (BadRequestException e) {
            model.addAttribute("message", StaticValues.ID_ERROR_MESSAGE);
            return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
        }
        return StaticValues.Templates.ADD_EDIT_MOVIE;
    }

    @GetMapping("/random")
    public String showRandomMovie(@RequestParam("genres") Set<Genre> specifiedGenres,
                                  Model model) {
        try {
            Movie randomMovie = movieService.getRandomMovie(specifiedGenres);
            model.addAttribute("movie", randomMovie);
            return StaticValues.Templates.MOVIE_PAGE;
        } catch (NotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
        }
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("movie")
                      @Validated(ValidationMarkers.OnCreate.class) Movie movie, BindingResult bindingResult,
                      HttpServletRequest request,
                      Model model) {
        if (bindingResult.hasErrors()) {
            return StaticValues.Templates.ADD_EDIT_MOVIE;
        }
        movie.setReleaseDate(DateConverter.convert(request));

        try {
            movieService.add(movie);
            model.addAttribute("message", "Movie successfully added");
        } catch (BadRequestException e) {
            model.addAttribute("message", StaticValues.ID_ERROR_MESSAGE);
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("movie")
                       @Validated(ValidationMarkers.OnUpdate.class) Movie movie, BindingResult bindingResult,
                       Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return StaticValues.Templates.ADD_EDIT_MOVIE;
        }
        movie.setReleaseDate(DateConverter.convert(request));

        try {
            movieService.edit(movie);
            model.addAttribute("message", "Movie successfully edited");
        } catch (BadRequestException e) {
            model.addAttribute("message", StaticValues.ID_ERROR_MESSAGE);
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        try {
            Movie movie = new Movie();
            movie.setId(id);
            movieService.delete(movie);
            model.addAttribute("message", "Movie successfully deleted");
        } catch (BadRequestException e) {
            model.addAttribute("message", StaticValues.ID_ERROR_MESSAGE);
        }
        return StaticValues.Templates.SPLASH_SCREEN_TEMPLATE;
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("search") String searchInput, Model model) {
        List<Movie> movies = movieService.searchMovies(searchInput);
        model.addAttribute("movies", movies);
        model.addAttribute("searchInput", searchInput);
        return "searchResult";
    }
}
