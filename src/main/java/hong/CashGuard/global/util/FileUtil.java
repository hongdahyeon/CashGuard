package hong.CashGuard.global.util;

/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : FileUtil
 * author         : work
 * date           : 2025-04-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */
public class FileUtil {

    public static String extension(String str, boolean withDot) {
        int lastIndex = (withDot) ? str.lastIndexOf(".") : str.lastIndexOf(".") + 1;
        return str.substring(lastIndex);
    }
}
