package hong.CashGuard.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.CashGuard.domain.calendar.service
 * fileName       : CgCalendarRestController
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 캘린더 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/calendar")
public class CgCalendarRestController {

    private final CgCalendarService service;
}
