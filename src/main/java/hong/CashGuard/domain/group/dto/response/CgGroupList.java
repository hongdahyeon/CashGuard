package hong.CashGuard.domain.group.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.response
 * fileName       : CgGroupList
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupList {

    private Long uid;
    private Long userUid;
    private String userNm;
    private String groupNm;
    private String isPrivate;
    private int approvedMember;
    private int totalMember;
    private Long regUid;
    private String regDt;
    private String regNm;
    private Long updtUid;
    private String updtDt;
    private String updtNm;
}
