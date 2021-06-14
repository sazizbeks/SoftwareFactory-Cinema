package kz.edu.astanait.gambit_cinema.controllers;

import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class AppController {
    private final IMovieService movieService;

    public AppController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping({"/index","/"})
    public String showIndex(Model model){
//        String[] genreNames = {"action","horror","fantasy","comedy","animation"};
//        HashMap<String,List<Movie>> movies = new HashMap<>();
//
//        for (String genreName : genreNames) {
//            movies.put(genreName,movieService.getMovieByGenre(genreName));
//        }
//
//        model.addAttribute("movies",movies);

        return "index";
    }


}
