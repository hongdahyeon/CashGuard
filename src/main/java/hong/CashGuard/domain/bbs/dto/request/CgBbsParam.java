package hong.CashGuard.domain.bbs.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.bbs.dto.request
 * fileName       : CgBbsParam
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 목록 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @Setter
public class CgBbsParam {

    private String bbsNm;

    private String bbsTpCd;

    @YorN
    private String useAt;
}
