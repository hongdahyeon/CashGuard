package hong.CashGuard.domain.code.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.code.dto.request
 * fileName       : CgCodeSave
 * author         : home
 * date           : 2025-03-31
 * description    : 코드 저장 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCodeSave {

    @NotBlank
    private String code;

    @NotBlank
    private String codeNm;

    @YorN(allowNull = false)
    private String useAt;
}