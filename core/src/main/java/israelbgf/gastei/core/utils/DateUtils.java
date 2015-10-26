package israelbgf.gastei.core.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.SECOND;

public class DateUtils {

    public static Date date(int year, int month){
        return date(year, month, 1, 0, 0);
    }

    public static Date date(int year, int month, int day){
        return date(year, month, day, 0, 0);
    }

    public static Date date(int year, int month, int day, int hour, int minutes){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month - 1, day, hour, minutes);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date lastDayOf(int year, int month) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(firstDayOf(year, month));
        calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date firstDayOf(int year, int month) {
        return date(year, month);
    }

    public static int dayOfTheMonth(Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
