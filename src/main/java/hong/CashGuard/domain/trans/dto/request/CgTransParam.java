package hong.CashGuard.domain.trans.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.request
 * fileName       : CgTransParam
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 목록 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @Setter
public class CgTransParam {

    private String startDate;
    private String endDate;

    private Long ledgerUid;

    private String categoryTp;      // INCOME, EXPENSE, SAVINGS

    private String transCd;         // EXPENSE, INCOME

    private Long userUid;
}
