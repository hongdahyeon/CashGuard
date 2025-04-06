package hong.CashGuard.domain.budget.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.budget.dto.request
 * fileName       : CgBudgetParam
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */

@Getter @Setter
public class CgBudgetParam {

    private Long userUid;

    private String periodType;

    private String transTpCd;
}
