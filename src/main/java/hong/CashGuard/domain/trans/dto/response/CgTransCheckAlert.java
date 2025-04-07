package hong.CashGuard.domain.trans.dto.response;

import hong.CashGuard.domain.code.BudgetTrans;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.response
 * fileName       : CgTransCheckAlert
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 초과 금액에 대한 체크 및 알림 전송을 위한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTransCheckAlert {

    private String userEmail;
    private String userNm;
    private String ledgerNm;
    private String transCd;
    private String transCdNm;
    private String exceedTotal;
    private String targetAmount;
    private String totalAmount;

    public void setAmountInfo(String exceedTotal, String targetAmount, String totalAmount) {
        this.exceedTotal = exceedTotal;
        this.targetAmount = targetAmount;
        this.totalAmount = totalAmount;
    }

    public void setTransInfo(BudgetTrans budgetTrans) {
        this.transCd = budgetTrans.name();
        this.transCdNm = budgetTrans.getText();
    }
}
