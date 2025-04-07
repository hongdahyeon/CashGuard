package hong.CashGuard.domain.calendar.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.calendar.dto.response
 * fileName       : CgCalendarView
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 캘린더 조회용 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCalendarView {

    private Long calendarUid;
    private Long userUid;
    private String userNm;
    private String startDate;
    private String endDate;
    private boolean allDay;
    private String bgColor;
    private String borderColor;
    private String textColor;
}
