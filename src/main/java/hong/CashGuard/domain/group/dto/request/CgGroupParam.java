package hong.CashGuard.domain.group.dto.request;

import hong.CashGuard.global.annotation.YorN;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.group.dto.request
 * fileName       : CgGroupParam
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 목록 조회 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @Setter
public class CgGroupParam {

    @YorN
    private String isPrivate;

    private String groupNm;
}
