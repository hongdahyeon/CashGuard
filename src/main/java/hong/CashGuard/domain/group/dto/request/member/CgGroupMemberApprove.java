package hong.CashGuard.domain.group.dto.request.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.request.member
 * fileName       : CgGroupMemberApprove
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 하위 사용자 승인 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupMemberApprove {

    @NotNull
    private Long groupUid;

    @NotEmpty
    private Long[] uids;
}
