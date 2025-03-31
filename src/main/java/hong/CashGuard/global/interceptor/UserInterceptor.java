package hong.CashGuard.global.interceptor;

import hong.CashGuard.global.auth.PrincipalDetails;
import hong.CashGuard.global.auth.dto.CgSessionUser;
import hong.CashGuard.global.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * packageName    : hong.CashGuard.global.interceptor
 * fileName       : UserInterceptor
 * author         : work
 * date           : 2025-03-27
 * description    : 요청을 컨트롤러에 보내기전(preHandle)에 실행되는 인터셉터
 *                  > 인증된 사용자 정보를 가져와서 요청에다가 담아준다.
 *                  => 그렇게 된다면 모든 컨트롤러에서 인증된 사용자 정보를 가져다가 사용할 수 있게 된다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        if(UserUtil.isAuthenticated(authentication)) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            CgSessionUser loginUser = details.getUser();
            request.setAttribute(UserUtil.REQUEST_USER_KEY, loginUser);
        }

        return true;
    }
}
