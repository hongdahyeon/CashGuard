package hong.CashGuard.global.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.handler
 * fileName       : CustomAutenticationHandler
 * author         : work
 * date           : 2025-04-01
 * description    : 인증되지 않은 사용자(로그인하지 않은 사용자) 접근의 경우 타게 된다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-01        work       최초 생성
 */

@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    }
}