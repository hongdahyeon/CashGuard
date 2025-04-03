package hong.CashGuard.domain.group.dto.response.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.response.member
 * fileName       : CgGroupMemberList
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 하위 사용자 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       패키지 이동
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupMemberList {

    private Long userUid;
    private String userId;
    private String userNm;
    private String userTel;
    private String userEmail;
    private String isApproved;
    private String approvedDt;
    private String joinDt;
    private String joinType;
    private String joinTypeNm;
}
