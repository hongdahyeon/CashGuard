package hong.CashGuard.domain.user.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.user.dto.request
 * fileName       : CgUserPassword
 * author         : work
 * date           : 2025-04-01
 * description    : 유저 비밀번호 변경 DTO
 *                  > { changeYn = Y } : 비밀번호 필수 입력
 *                  > { changeYn = N } : 비밀번호 미입력
 *                  => 비밀번호 마지막 변경일자 90일 연장
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-01        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgUserPassword {

    private String password;

    @NotBlank
    private String userId;

    @YorN(allowNull = false)
    private String changeYn;
}
