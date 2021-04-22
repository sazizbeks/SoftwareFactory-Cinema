package kz.edu.astanait.gambit_cinema.rest;

import kz.edu.astanait.gambit_cinema.dto.favoritelist.FavoriteMovieIdsDto;
import kz.edu.astanait.gambit_cinema.dto.favoritelist.UpdateFavoriteResponse;
import kz.edu.astanait.gambit_cinema.services.interfaces.IMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppAPI {
    private final IMovieService movieService;

    public AppAPI(IMovieService movieService) {
        this.movieService = movieService;
    }

    @ResponseBody
    @PostMapping("/update-favorite")
    public ResponseEntity<?> updateFavorite(@RequestBody FavoriteMovieIdsDto dto) {
        boolean result = movieService.addOrDeleteMovieInFavoriteList(dto.getUserId(), dto.getMovieId());

        return ResponseEntity.ok(
                UpdateFavoriteResponse.builder().
                        resultMessage(result ? "Successfully added to favorite list" : "Successfully removed from favorite list")
                        .isAdded(result)
                        .build()
        );
    }
}
