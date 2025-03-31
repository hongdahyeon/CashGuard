package hong.CashGuard.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : TimeUtil
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */
public class TimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateTime TODAY = LocalDateTime.now();

    public static String nowDate(){
        return TODAY.format(DATE_TIME_FORMATTER);
    }

    public static boolean isXYearAfter(String compareDateString, int year) {
        LocalDateTime compareDate = LocalDateTime.parse(compareDateString, DATE_TIME_FORMATTER);
        LocalDateTime oneYearAgo = TODAY.minus(year, ChronoUnit.YEARS);
        return !compareDate.isBefore(oneYearAgo);
    }

    public static boolean dateCompare(String futureDateString) {
        LocalDateTime futureDate = LocalDateTime.parse(futureDateString, DATE_TIME_FORMATTER);
        return futureDate.isAfter(TODAY);
    }

    public static String daysAfter(int after){
        LocalDateTime futureDate = TODAY.plusDays(after);
        return futureDate.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime daysAfter_Date(int after) {
        return TODAY.plusDays(after);
    }
}
