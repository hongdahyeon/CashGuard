package hong.CashGuard.domain.calendar.domain;

import hong.CashGuard.domain.calendar.dto.request.CgCalendarChange;
import hong.CashGuard.domain.calendar.dto.request.CgCalendarSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.calendar.domain
 * fileName       : CgCalendar
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 캘린더 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       수입/지출 캘린더 수정용 생성자 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCalendar {

    private Long uid;
    private Long transUid;
    private String startDate;
    private String endDate;
    private boolean allDay;
    private String bgColor;
    private String borderColor;
    private String textColor;
    private Long userUid;

    /**
     * @method      CgCalendar 생성자 1
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 캘린더 저장용 생성자
    **/
    public CgCalendar(CgCalendarSave request) {
        this.transUid = request.getTransUid();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.allDay = request.isAllDay();
        this.bgColor = "red";
        this.borderColor = "white";
        this.textColor = "black";
        this.userUid = request.getUserUid();
    }

    /**
     * @method      CgCalendar 생성자 2
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 캘린더 수정용 생성자
    **/
    public CgCalendar(CgCalendarChange request) {
        this.transUid = request.getTransUid();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.allDay = request.isAllDay();
    }
}
