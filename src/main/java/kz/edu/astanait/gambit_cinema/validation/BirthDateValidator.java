package kz.edu.astanait.gambit_cinema.validation;

import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator {
    public static boolean isValid(Date date) {
        int permittedAge = 12;
        Calendar calendar = Calendar.getInstance();

        Date permittedBeforeDate = new Date(
                calendar.get(Calendar.YEAR) - permittedAge,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        return date.before(permittedBeforeDate);
    }
}
