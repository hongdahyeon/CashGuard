package hong.CashGuard.domain.group.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
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
 * 2025-04-04        work       {regUid, regDt, regNm, updtUid, updtDt, updtNm} => AuditMetaData 으로 빼기
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupList extends AuditMetaData {

    private Long uid;
    private Long userUid;
    private String userNm;
    private String groupNm;
    private String isPrivate;
    private int approvedMember;
    private int totalMember;

}
