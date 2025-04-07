package hong.CashGuard.domain.budget.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.budget.dto.request
 * fileName       : CgBudgetSave
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       startDate 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBudgetSave {

    @NotBlank
    private String startDate;

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

    @NotNull
    private Long ledgerUid;
}
