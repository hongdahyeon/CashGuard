package hong.CashGuard.domain.budget.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.budget.dto.response
 * fileName       : CgBudgetLIst
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 목룍 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBudgetList extends AuditMetaData {

    private Long budgetUid;
    private Long userUid;
    private String userNm;
    private String periodType;
    private String periodTypeNm;
    private long periodVal;
    private String transTpCd;
    private String transTpCdNm;
    private long transTargetAmount;
    private String exceedAlert;
    private Long ledgerUid;
    private String ledgerNm;
}
