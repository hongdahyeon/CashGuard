package hong.CashGuard.domain.bbs.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.bbs.dto.request
 * fileName       : CgBbsChange
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 수정 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBbsChange {

    @NotBlank
    private String bbsTpCd;

    @NotBlank
    private String bbsNm;

    @YorN(allowNull = false)
    private String useAt;
}
