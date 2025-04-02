package hong.CashGuard.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.user.dto.request
 * fileName       : CgUserChange
 * author         : work
 * date           : 2025-04-01
 * description    : 사용자 정보 수정 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-01        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgUserChange {

    private String password;

    @NotBlank
    private String userNm;

    @NotBlank
    private String userTel;

    @NotBlank
    private String userEmail;

}
