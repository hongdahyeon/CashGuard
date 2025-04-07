package hong.CashGuard.domain.trans.domain;

import hong.CashGuard.domain.trans.dto.request.CgTransChange;
import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
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
 * 2025-04-07        work       * 삭제여부 추가
 *                              * 수입/지출 수정,삭제 생성자 추가
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
    private Long deleteAt;
    
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

    /**
     * @method      CgTrans 생성자 2
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 수정용 생성자
    **/
    public CgTrans changeTrans(Long uid, CgTransChange request) {
        this.uid = uid;
        this.categoryUid = StringUtil.getOrDefault(request.getCategoryUid(), this.categoryUid);
        this.transCd = StringUtil.getOrDefault(request.getTransCd(), this.transCd);
        this.amount = StringUtil.getOrDefault(request.getAmount(), this.amount);
        this.description = StringUtil.getOrDefault(request.getDescription(), null);
        this.transDate = StringUtil.getOrDefault(request.getTransDate(), this.transDate);
        this.note = StringUtil.getOrDefault(request.getNote(), null);
        return this;
    }

    /**
     * @method      CgTrans 생성자 3
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 삭제용 생성자
    **/
    public CgTrans(Long uid) {
        this.uid = uid;
    }
}
