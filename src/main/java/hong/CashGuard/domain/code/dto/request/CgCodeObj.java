package hong.CashGuard.domain.code.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.code.dto.request
 * fileName       : CgCodeObj
 * author         : work
 * date           : 2025-04-02
 * description    : CgCodeMerge 용
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCodeObj {

    private Long uid;
    private String upperCode;

    @NotBlank
    private String code;

    @NotBlank
    private String codeNm;

    @YorN(allowNull = false)
    private String useAt;
}
