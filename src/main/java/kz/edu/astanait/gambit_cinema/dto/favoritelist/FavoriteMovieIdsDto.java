package kz.edu.astanait.gambit_cinema.dto.favoritelist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteMovieIdsDto {
    private Long userId;
    private Long movieId;
}
