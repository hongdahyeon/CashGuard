package hong.CashGuard.domain.ledger.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.ledger.dto.request
 * fileName       : CgLedgerSave
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 생성 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgLedgerSave {

    @NotBlank
    private String ledgerNm;

    @YorN(allowNull = false)
    private String useAt;

    private String ledgerNote;
}
