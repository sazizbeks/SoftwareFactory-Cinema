package kz.edu.astanait.gambit_cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long userId;
    private int month;
    private Calendar expirationDate;
}
