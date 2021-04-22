package kz.edu.astanait.gambit_cinema.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kz.edu.astanait.gambit_cinema.dto.moviedb.ApiMovieDto;
import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.tools.DateConverter;
import kz.edu.astanait.gambit_cinema.tools.StaticValues;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonParser {
    private final Gson gson = new Gson();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Genre> parseGenres() throws IOException {
        return objectMapper.readValue(getFile("genres.json"), new TypeReference<>() {
        });
    }

    public List<Movie> parseMovies() throws IOException {
        String imgUrlPrepend = "https://image.tmdb.org/t/p/w500";

        ApiMovieDto[] movieDtosArr = gson.fromJson(new FileReader(StaticValues.jsonFolder + "movies.json"), ApiMovieDto[].class);

        List<ApiMovieDto> movieDtos = new LinkedList<>(Arrays.asList(movieDtosArr));

        List<Movie> movies = new LinkedList<>();
        movieDtos.forEach(dto -> {
            Movie movie = new Movie();
            movie.setName(dto.getTitle());

            movie.setReleaseDate(DateConverter.convert(dto.getRelease_date()));

            Set<Genre> genreSet = new HashSet<>();
            for (Long genreId : dto.getGenre_ids()) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genreSet.add(genre);
            }
            movie.setGenres(genreSet);

            movie.setDescription(dto.getOverview());
            movie.setImgUrl(imgUrlPrepend + dto.getPoster_path());
            movie.setAgeRating(dto.getAdult() ? (byte) 18 : null);
            movies.add(movie);
        });
        return movies;
    }

    private File getFile(String jsonFile) {
        return new File(StaticValues.jsonFolder + jsonFile);
    }

}
