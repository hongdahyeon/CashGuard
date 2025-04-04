package hong.CashGuard.domain.ledger.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.ledger.dto.response
 * fileName       : CgLedgerList
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-04        work       {regUid, regDt, regNm, updtUid, updtDt, updtNm} => AuditMetaData 으로 빼기
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgLedgerList extends AuditMetaData {

    private Long uid;
    private Long userUid;
    private String userNm;
    private String ledgerNm;
    private String ledgerNote;
    private String useAt;
    private String deleteAt;

}
