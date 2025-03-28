package hong.CashGuard.global.util;

import hong.CashGuard.global.auth.dto.CgSessionUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : UserUtil
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */
public class UserUtil {

    public static final String REQUEST_USER_KEY = "user";

    public static CgSessionUser getLoginUser() {
        return getUser(WebUtil.nowRequest());
    }

    public static Long getLoginUserUid() {
        CgSessionUser user = getUser(WebUtil.nowRequest());
        return user.getUid();
    }

    public static CgSessionUser getUser(HttpServletRequest request) {
        Object user = request.getAttribute(REQUEST_USER_KEY);
        return user != null ? (CgSessionUser) user : null;
    }

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() != "anonymousUser";
    }
}
