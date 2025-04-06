package hong.CashGuard.domain.trans.domain;

import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.domain.trans.service.CgTransService;
import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.trans.domain
 * fileName       : CgTrans
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTrans extends AuditBean {

    private Long uid;
    private Long ledgerUid;
    private Long categoryUid;
    private String transCd;     // [BudgetTrans] 수입, 지출
    private long amount;
    private String description;
    private String transDate;
    private String note;
    
    /**
     * @method      CgTrans 생성자 1
     * @author      note
     * @date        2025-04-06
     * @deacription 수입/지출 저장용 생성자
    **/
    public CgTrans(CgTransSave request) {
        this.ledgerUid = request.getLedgerUid();
        this.categoryUid = request.getCategoryUid();
        this.transCd = request.getTransCd();
        this.amount = request.getAmount();
        this.description = request.getDescription();
        this.transDate = request.getTransDate();
        this.note = request.getNote();
    }

}
