package kz.edu.astanait.gambit_cinema.tools;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

public class DateConverter {
    /**
     * Gets day, month, year from HttpServletRequest and converts them to Date
     *
     * @param request HttpServletRequest
     * @return converted Calendar
     */
    public static Calendar convert(HttpServletRequest request) {
        int day = Integer.parseInt(request.getParameter("day"));
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        return getCalendar(day, month, year);
    }

    public static Calendar getCalendar(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar;
    }

    public static Calendar convert(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8));
        return getCalendar(day, month, year);
    }
}
