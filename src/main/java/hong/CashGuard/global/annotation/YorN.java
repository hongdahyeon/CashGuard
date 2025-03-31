package hong.CashGuard.global.annotation;

import hong.CashGuard.global.validator.YorNValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * packageName    : hong.CashGuard.global.annotation
 * fileName       : YorN
 * author         : home
 * date           : 2025-03-31
 * description    : useAt 와 같이 Y,N의 값을 갖는 용도의 Validator
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */
@Documented
@Constraint(validatedBy = YorNValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface YorN {
    String message() default "값은 'Y' 또는 'N'이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** null 허용 여부 (기본값: true) */
    boolean allowNull() default true;
}
