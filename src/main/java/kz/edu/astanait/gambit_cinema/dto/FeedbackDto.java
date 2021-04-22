package kz.edu.astanait.gambit_cinema.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FeedbackDto {
    private Long id;
    private UserDto user;
    private String comment;
}