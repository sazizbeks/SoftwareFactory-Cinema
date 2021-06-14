package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.parser.ApiParser;
import kz.edu.astanait.gambit_cinema.parser.JsonParser;
import kz.edu.astanait.gambit_cinema.repositories.GenreRepository;
import kz.edu.astanait.gambit_cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * FillDbAPI rest controller class
 * REST API class for data parsing and database fill
 */
@RestController
@RequestMapping("/api/admin/fillDB")
public class FillDbAPI {
    private final JsonParser jsonParser;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public FillDbAPI(JsonParser jsonParser, GenreRepository genreRepository, MovieRepository movieRepository) {
        this.jsonParser = jsonParser;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * /api/admin/fillDB/genres
     * Adds genres to DB
     * @return response
     */
    @GetMapping("/genres")
    public ResponseEntity<?> fillGenres() {
        if (genreRepository.count() == 0) {
            try {
                List<Genre> genreList = jsonParser.parseGenres();
                genreRepository.saveAll(genreList);
                return ResponseEntity.ok("Genres successfully added");
            } catch (IOException e) {
                return ResponseEntity.status(404).body("File not found");
            }
        }
        return ResponseEntity.ok("Genres already exist");
    }

    /**
     * /api/admin/fillDB/movies
     * Adds movies to DB
     * @return response
     */
    @GetMapping("/movies")
    public ResponseEntity<?> fillMovies() {
        if (movieRepository.count() == 0) {
            try {
                movieRepository.saveAll(jsonParser.parseMovies());
                return ResponseEntity.ok("Movies successfully added");
            } catch (IOException e) {
                return ResponseEntity.status(404).body("File not found");
            }
        }
        return ResponseEntity.ok("Movies already exist");
    }
}
