package hong.CashGuard.domain.group.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.request
 * fileName       : CgGroupSave
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 정보 저장 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupSave {

    @NotBlank
    private String groupNm;

    @YorN(allowNull = false)
    private String isPrivate;
}
