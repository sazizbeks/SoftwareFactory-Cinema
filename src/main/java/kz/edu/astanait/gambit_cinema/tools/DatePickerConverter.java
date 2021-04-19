package kz.edu.astanait.gambit_cinema.tools;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

public class DatePickerConverter {
    /**
     * Gets day, month, year from HttpServletRequest and converts them to Date
     * @param request HttpServletRequest
     * @return converted Calendar
     */
    public static Calendar convertRequestParams(HttpServletRequest request) {

        int day = Integer.parseInt(request.getParameter("day"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);

        return calendar;
    }
}
