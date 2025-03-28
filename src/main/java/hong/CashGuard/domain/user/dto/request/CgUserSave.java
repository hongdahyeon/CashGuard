package hong.CashGuard.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.user.dto.request
 * fileName       : CgUserSave
 * author         : work
 * date           : 2025-03-27
 * description    : 유저 저장을 위한 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */


@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgUserSave {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String userNm;

    @NotBlank
    private String userTel;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String role;

}
