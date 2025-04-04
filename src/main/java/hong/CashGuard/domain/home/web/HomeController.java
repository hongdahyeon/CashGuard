package hong.CashGuard.domain.home.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.CashGuard.domain.home.web
 * fileName       : HomeController
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-03        work       패키지 이동
 */

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
