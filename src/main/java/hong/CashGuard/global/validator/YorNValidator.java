package hong.CashGuard.global.validator;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * packageName    : hong.CashGuard.global.validator
 * fileName       : YorNValidator
 * author         : home
 * date           : 2025-03-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */
public class YorNValidator implements ConstraintValidator<YorN, String> {
    private boolean allowNull;

    @Override
    public void initialize(YorN constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        return value.equals("Y") || value.equals("N");
    }
}