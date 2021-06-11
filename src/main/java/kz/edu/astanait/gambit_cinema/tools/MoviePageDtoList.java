package kz.edu.astanait.gambit_cinema.tools;

import kz.edu.astanait.gambit_cinema.dto.MoviePageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePageDtoList {
    private List<MoviePageDto> list;
}
