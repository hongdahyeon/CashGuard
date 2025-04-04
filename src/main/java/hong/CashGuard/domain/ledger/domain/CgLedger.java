package hong.CashGuard.domain.ledger.domain;

import hong.CashGuard.domain.ledger.dto.request.CgLedgerChange;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.ledger.domain
 * fileName       : CgLedger
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgLedger extends AuditBean {

    private Long uid;
    private Long userUid;
    private String useAt;
    private String deleteAt;
    private String ledgerNm;
    private String ledgerNote;
    
    /**
     * @method      CgLedger 생성자 1
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 생성을 위한 생성자
    **/
    public CgLedger(Long userUid, CgLedgerSave request) {
        this.userUid = userUid;
        this.useAt = request.getUseAt();
        this.ledgerNm = request.getLedgerNm();
        this.ledgerNote = request.getLedgerNote();
    }

    /**
     * @method      CgLedger 생성자 2
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 정보 수정을 위한 생성자
    **/
    public CgLedger changeLedger(Long uid, CgLedgerChange request) {
        this.uid = uid;
        this.useAt = StringUtil.getOrDefault(request.getUseAt(), this.useAt);
        this.ledgerNm = StringUtil.getOrDefault(request.getLedgerNm(), this.ledgerNm);
        this.ledgerNote = StringUtil.getOrDefault(request.getLedgerNote(), null);
        return this;
    }

    /**
     * @method      CgLedger 생성자 3
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 정보 삭제를 위한 생성자
    **/
    public CgLedger(Long uid) {
        this.uid = uid;
    }
}
