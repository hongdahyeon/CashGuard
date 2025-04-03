package hong.CashGuard.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.CashGuard.global.auth
 * fileName       : AuthenticationService
 * author         : work
 * date           : 2025-04-03
 * description    : 초대링크를 통한 접근시, 자동 로그인을 해주기 위한 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PrincipalDetailsService detailsService;

    public void login(String userId, HttpServletRequest request) {
        UserDetails userDetails = detailsService.loadUserByUsername(userId);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // {SecurityContextHolder} 에 인증 정보 저장
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}
