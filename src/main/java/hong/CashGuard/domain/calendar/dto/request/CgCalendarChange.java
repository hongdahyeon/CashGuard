package hong.CashGuard.domain.calendar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.calendar.dto.request
 * fileName       : CgCalendarChange
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 캘린더 수정용 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCalendarChange {

    @NotNull
    private Long transUid;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @NotNull
    private boolean allDay;

    @Builder(builderMethodName = "updateBuilder")
    public CgCalendarChange(Long transUid, String startDate, String endDate) {
        this.transUid = transUid;
        this.startDate= startDate;
        this.endDate = endDate;
        this.allDay = false;
    }

}
