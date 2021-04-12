package kz.edu.astanait.gambit_cinema.services.interfaces;

import kz.edu.astanait.gambit_cinema.exceptions.BadRequestException;
import kz.edu.astanait.gambit_cinema.models.Movie;

public interface IMovieService {
    void add(Movie movie) throws BadRequestException;
    void edit(Movie movie) throws BadRequestException;
    void delete(Movie movie) throws BadRequestException;
    Movie getById(Long id) throws BadRequestException;
}
