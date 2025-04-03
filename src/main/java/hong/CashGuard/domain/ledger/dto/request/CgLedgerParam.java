package hong.CashGuard.domain.ledger.dto.request;

import hong.CashGuard.global.annotation.YorN;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.ledger.dto.request
 * fileName       : CgLedgerParam
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 목록 조회 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Getter @Setter
public class CgLedgerParam {

    private String ledgerNm;

    @YorN
    private String useAt;

    private Long userUid;
}
