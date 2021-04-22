package kz.edu.astanait.gambit_cinema.dto.favoritelist;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateFavoriteResponse {
    private boolean isAdded;
    private String resultMessage;
}
