package hong.CashGuard.domain.budget.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.budget.dto.request
 * fileName       : CgBudgetChange
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 수정 요청 DTO
 *                  -> 수정 시점에는 매핑된 가계부 정보는 변경이 불가능하다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBudgetChange {

    @NotBlank
    private String periodType;

    @NotNull
    private long periodVal;

    @NotBlank
    private String transTpCd;

    @NotNull
    private long transTargetAmount;

    @YorN(allowNull = false)
    private String exceedAlert;

}
