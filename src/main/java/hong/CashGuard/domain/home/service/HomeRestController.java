package hong.CashGuard.domain.home.service;

import hong.CashGuard.global.hong.ollama.OllamaChatService;
import hong.CashGuard.global.util.AESUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.home.service
 * fileName       : HomeRestController
 * author         : work
 * date           : 2025-03-28
 * description    : 공통 필요한 RestController
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-28        work       최초 생성
 * 2025-04-03        work       * 패키지 이동
 *                              * 초대 API 추가 : /api/invite-link/{token}
 * 2025-04-08        work       "/ask" API 추가 ( Spring AI Ollama 이용 )
 *                              => 다시 제거 (CgChatRestController 로 뺴둠)
 */

@RestController
@RequiredArgsConstructor
public class HomeRestController {

    private final HomeService homeService;
    private final OllamaChatService chatService;

    @GetMapping("/csrf")
    public Map<String, String> getCSRFToken(HttpServletRequest req) {
        CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("token", csrf.getToken());
        return response;
    }

    /**
     *
     * 이메일을 통한 초대하기
     *
     * @api         [GET] /api/invite-link/{token}
     * @author      work
     * @date        2025-04-03
    **/
    @GetMapping("/api/invite-link/{token}")
    public ResponseEntity inviteLink(@PathVariable("token") String token, HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> map = AESUtil.decrypt(token);
        Long uid = (Long) map.get("uid");
        String reasonCode = (String) map.get("code");
        return homeService.inviteUser(token, uid, reasonCode, req, res);
    }

}
