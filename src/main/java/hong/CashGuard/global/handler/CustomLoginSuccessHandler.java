package hong.CashGuard.global.handler;

import hong.CashGuard.domain.user.service.CgSecurityUserService;
import hong.CashGuard.domain.user.service.CgUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.handler
 * fileName       : CustomLoginSuccessHandler
 * author         : work
 * date           : 2025-03-27
 * description    : 로그인 성공 핸들러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CgSecurityUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userId = request.getParameter("userId");
        if(userId == null) {

            // TODO : oAuth Login

        } else {

            log.info("======================= Login User: {} ===========================", userId);
            userService.resetLastLoginDtAndPwdFailCnt(userId);
        }

        String landingPage = "/";
        response.sendRedirect(landingPage);
    }
}
