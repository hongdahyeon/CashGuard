package hong.CashGuard.global.util;

import java.util.Random;

/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : StringUtil
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-01        work       {getOrDefault} 메소드 추가
 * 2025-04-07        work       {numberFormat} 메소드 추가 => 돈 단위로 출력
 */
public class StringUtil {

    public static boolean startswith(String str, String prefix) {
        if(str == null || str.length() == 0) return false;
        else {
            return str.startsWith(prefix);
        }
    }

    public static boolean equal(String str1, String str2) {
        if(str1 == null || str2 == null) return false;
        else {
            return str1.equals(str2) && str2.equals(str1);
        }
    }

    public static String fixLength(String src, int limit) {
        if (src.length() > limit) {
            return src.substring(0, limit) + "...";
        }
        return src;
    }

    public static String random(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            password.append(randomChar);
        }

        return password.toString();
    }

    public static <T> T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }

    public static String numberFormat(long amount) {
        return String.format("%,d", amount);
    }
}
