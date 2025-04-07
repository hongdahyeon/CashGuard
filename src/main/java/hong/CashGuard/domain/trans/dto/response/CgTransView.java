package hong.CashGuard.domain.trans.dto.response;

import hong.CashGuard.domain.calendar.dto.response.CgCalendarView;
import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.response
 * fileName       : CgTrnasView
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 단건 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTransView extends AuditMetaData {

    private Long transUid;
    private Long ledgerUid;
    private String ledgerNm;
    private Long categoryUid;
    private String categoryCd;
    private String categoryTp;
    private String categoryTpNm;
    private boolean categoryTpIsAdd;
    private String transCd;
    private String transCdNm;
    private long amount;
    private String description;
    private String transDate;
    private String note;
    private CgCalendarView calendar;
    public void setCalendar(CgCalendarView calendar) {
        this.calendar = calendar;
    }
}
