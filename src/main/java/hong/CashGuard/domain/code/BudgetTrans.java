package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : BudgetTrans
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 -> 수입 / 지출
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       [getBudgetTrans] 추가
 */
@Getter
public enum BudgetTrans {

    INCOME("수입"),
    EXPENSE("지출");
    private String text;

    BudgetTrans(String text) {
        this.text = text;
    }

    public static boolean isValidTrans(String trans) {
        for( BudgetTrans budgetTrans : BudgetTrans.values() ) {
            if(budgetTrans.name().equals(trans)) {
                return true;
            }
        }
        return  false;
    }

    public static BudgetTrans getBudgetTrans(String code) {
        for( BudgetTrans budgetTrans : BudgetTrans.values() ) {
            if(budgetTrans.name().equals(code)) {
                return budgetTrans;
            }
        }
        return  null;
    }
}
