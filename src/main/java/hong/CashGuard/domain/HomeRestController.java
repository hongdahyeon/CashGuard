package hong.CashGuard.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.user
 * fileName       : HomeRestController
 * author         : work
 * date           : 2025-03-28
 * description    : 공통 필요한 RestController
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-28        work       최초 생성
 */

@RestController
public class HomeRestController {

    @GetMapping("/csrf")
    public Map<String, String> getCSRFToken(HttpServletRequest req) {
        CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("token", csrf.getToken());
        return response;
    }

}
