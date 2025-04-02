package hong.CashGuard.global.handler;

import hong.CashGuard.global.bean.error.ErrorResponse;
import hong.CashGuard.global.config.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : hong.CashGuard.global.handler
 * fileName       : CustomAuthenticationHandler
 * author         : work
 * date           : 2025-04-01
 * description    : 인증되지 않은 사용자(로그인하지 않은 사용자) 접근의 경우 타게 된다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-01        work       최초 생성
 */

@Component
public class CustomAuthenticationHandler extends LoginUrlAuthenticationEntryPoint {

    public CustomAuthenticationHandler() {
        super(Paths.LOGIN);
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
            throws IOException, ServletException {

        // AJAX 요청인지 검사
        String ajaxHeader = req.getHeader("X-Requested-With");
        if( "XMLHttpRequest".equals(ajaxHeader) ) {

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = res.getWriter();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.", "Unauthorized");
            String jsonResponse = ErrorResponse.toJson(errorResponse);
            writer.write(jsonResponse);
            writer.flush();

        } else {

            // 기본 동작 (로그인 페이지로 리디렉션)
            super.commence(req, res, authException);

        }
    }
}