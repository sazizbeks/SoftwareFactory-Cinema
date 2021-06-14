package kz.edu.astanait.gambit_cinema.repositories;

import kz.edu.astanait.gambit_cinema.models.Genre;
import kz.edu.astanait.gambit_cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> getDistinctByGenresIn(Set<Genre> genres);

    List<Movie> findMoviesByNameContainingIgnoreCase(String searchInput);

    @Query(value = "select case when count(f)>0 then true else false end from favorite_movies f where user_id=:userId and movie_id=:movieId",
            nativeQuery = true)
    boolean favoriteMovieExists(Long userId, Long movieId);

    @Modifying
    @Query(value = "insert into favorite_movies(user_id, movie_id) values (:userId,:movieId)",
            nativeQuery = true)
    void addMovieToFavoriteList(Long userId, Long movieId);

    @Modifying
    @Query(value = "delete from favorite_movies where user_id=:userId and movie_id=:movieId",
            nativeQuery = true)
    void deleteMovieFromFavoriteList(Long userId, Long movieId);

    @Query(value = "select m.* from movies m join favorite_movies fm on m.id = fm.movie_id where fm.user_id =:id", nativeQuery = true)
    Set<Movie> getFavList(Long id);

    List<Movie> findMovieByGenresContaining(Genre genre);
}
