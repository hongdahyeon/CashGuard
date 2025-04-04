package hong.CashGuard.domain.group.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.response
 * fileName       : CgGroupInfo
 * author         : work
 * date           : 2025-04-04
 * description    : 세션 유저에 담을 그룹 정보 조회용 응답 DTO
 *                  => 활성화된 그룹만 담긴다
 *                  -> 활성화된 그룹 = 승인된 멤버가 3명 이상인 그룹
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupInfo {

    private Long uid;
    private String groupNm;
    private boolean isExponent; // 대표자 여부
}
