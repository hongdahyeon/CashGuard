package hong.CashGuard.global.auth.dto;

import hong.CashGuard.domain.group.dto.response.CgGroupInfo;
import lombok.Getter;

/**
 * packageName    : hong.CashGuard.global.auth.dto
 * fileName       : CgSessionGroup
 * author         : work
 * date           : 2025-04-04
 * description    : 세션 유저에 담길 유저의 그룹 정보
 *                  => 자신이 생성한 그룹, 자신이 속한 그룹 모두가 들어간다.
 *                  -> 활성화된 그룹만 조회된다. (승인된 멤버가 3명 이상인 그룹)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter
public class CgSessionGroup {

    private Long uid;
    private String groupNm;
    private boolean isExponent;

    public CgSessionGroup(CgGroupInfo groupInfo) {
        this.uid = groupInfo.getUid();
        this.groupNm = groupInfo.getGroupNm();
        this.isExponent = groupInfo.isExponent();
    }
}
