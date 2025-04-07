package hong.CashGuard.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : WebUtil
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-07        work       null 로직 분리
 */
public class WebUtil {

    /**
     * @method      nowRequest
     * @author      work
     * @date        2025-03-27
     * @deacription 아무 위치에서나 HttpServletRequest 얻기
    **/
    public static HttpServletRequest nowRequest() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs == null) return null;
        return ((ServletRequestAttributes) attrs).getRequest();
    }
}
