package hong.CashGuard.domain.group.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.request
 * fileName       : CgGroupChange
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 정보 수정 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       카테고리 UID 목록 받는 필드 추가
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupChange {

    @NotBlank
    private String groupNm;

    @YorN(allowNull = false)
    private String isPrivate;

    @NotEmpty
    private Long[] uids; // 카테고리 UID
}