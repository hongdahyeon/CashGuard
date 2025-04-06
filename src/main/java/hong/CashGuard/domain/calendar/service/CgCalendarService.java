package hong.CashGuard.domain.calendar.service;

import hong.CashGuard.domain.calendar.domain.CgCalendar;
import hong.CashGuard.domain.calendar.domain.CgCalendarMapper;
import hong.CashGuard.domain.calendar.dto.request.CgCalendarSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.calendar.service
 * fileName       : CgCalendarService
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 캘린더 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CgCalendarService {

    private final CgCalendarMapper mapper;

    /**
     * @method      saveCalendar
     * @author      note
     * @date        2025-04-06
     * @deacription 수입/지출 캘린더 저장
    **/
    @Transactional
    public void saveCalendar(CgCalendarSave request) {
        mapper.insert(new CgCalendar(request));
    }
}
