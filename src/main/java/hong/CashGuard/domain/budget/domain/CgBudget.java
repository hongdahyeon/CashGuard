package hong.CashGuard.domain.budget.domain;

import hong.CashGuard.domain.budget.dto.request.CgBudgetChange;
import hong.CashGuard.domain.budget.dto.request.CgBudgetSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
import hong.CashGuard.global.util.TimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.budget.domain
 * fileName       : CgBudget
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 엔티티
 *                  ** [periodType] 유형에 따른 [periodVal] 값으로 목표 기간을 잡는다
 *                  ** [transTpCd] 유형에 따른 [transTargetAmount] 값으로 목표 금액을 잡는다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       * [sendAlarm] 필드 추가
 *                              * [startDate] 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBudget extends AuditBean {

    private Long uid;
    private Long userUid;
    private String startDate;
    private String periodType;          // [BudgetPeriod] 예산 기간 유형 : 년 월 일
    private long periodVal;             // 예산 기간 값   : 유형 값
    private String transTpCd;           // [BudgetTrans] 수입 or 지출 코드
    private long transTargetAmount;     // 목표 금액
    private String exceedAlert;
    private String sendAlarm;
    private Long ledgerUid;
    private String deleteAt;

    /**
     * @method      CgBudget 생성자 1
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 저장 생성자
    **/
    public CgBudget(CgBudgetSave request, Long loginUserUid) {
        this.userUid = loginUserUid;
        this.startDate = TimeUtil.addTimeFormat(request.getStartDate()).toString();
        this.periodType = request.getPeriodType();
        this.periodVal = request.getPeriodVal();
        this.transTpCd = request.getTransTpCd();
        this.transTargetAmount = request.getTransTargetAmount();
        this.exceedAlert = request.getExceedAlert();
        this.ledgerUid = request.getLedgerUid();
    }

    /**
     * @method      CgBudget 생성자 2
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 수정 생성자
    **/
    public CgBudget changeBudget(Long uid, CgBudgetChange request) {
        this.uid = uid;
        this.userUid = this.userUid;

        String dateString = StringUtil.getOrDefault(request.getStartDate(), this.startDate);
        this.startDate = TimeUtil.addTimeFormat(dateString).toString();

        this.periodType = StringUtil.getOrDefault(request.getPeriodType(), this.periodType);
        this.periodVal = StringUtil.getOrDefault(request.getPeriodVal(), this.periodVal);
        this.transTpCd = StringUtil.getOrDefault(request.getTransTpCd(), this.transTpCd);
        this.transTargetAmount = StringUtil.getOrDefault(request.getTransTargetAmount(), this.transTargetAmount);
        this.exceedAlert = StringUtil.getOrDefault(request.getExceedAlert(), this.exceedAlert);
        this.ledgerUid = this.ledgerUid;
        return this;
    }

    /**
     * @method      CgBudget 생성자 3
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 삭제 생성자
    **/
    public CgBudget(Long uid) {
        this.uid = uid;
    }
}
