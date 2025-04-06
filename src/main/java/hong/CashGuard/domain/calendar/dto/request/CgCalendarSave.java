package hong.CashGuard.domain.calendar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.calendar.dto.request
 * fileName       : CgCalendarSave
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 캘린더 저장용 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCalendarSave {

    @NotNull
    private Long transUid;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @NotNull
    private boolean allDay;

    @NotNull
    private Long userUid;

    @Builder(builderMethodName = "insertCalendar")
    public CgCalendarSave(Long transUid, String startDate, String endDate, Long userUid) {
        this.transUid = transUid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = false;
        this.userUid = userUid;
    }
}
