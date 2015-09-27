package israelbgf.gastei.core.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.SECOND;

public class DateUtils {

    public static Date createDate(int year, int month, int day, int hour, int minutes){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month - 1, day, hour, minutes);
        calendar.set(SECOND, 0);
        return calendar.getTime();
    }

}
