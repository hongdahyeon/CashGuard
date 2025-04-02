package hong.CashGuard.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.request
 * fileName       : CgGroupInvite
 * author         : work
 * date           : 2025-04-02
 * description    : 사용자를 그룹에 초대하기 위한 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor
public class CgGroupInvite {

    @NotBlank
    private String recipientNm;

    @NotBlank
    private String recipientEmail;

    @NotNull
    private Long groupUid;
}
