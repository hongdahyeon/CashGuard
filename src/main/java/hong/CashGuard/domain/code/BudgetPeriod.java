package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : BudgetPeriod
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 기간
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Getter
public enum BudgetPeriod {

    YEAR("년"),
    MONTH("월"),
    DAYS("일");

    private String text;

    BudgetPeriod(String text) {
        this.text = text;
    }

    public static boolean isValidPeriod(String period) {
        for( BudgetPeriod budgetPeriod : BudgetPeriod.values() ) {
            if(budgetPeriod.name().equals(period)) {
                return true;
            }
        }
        return  false;
    }
}
