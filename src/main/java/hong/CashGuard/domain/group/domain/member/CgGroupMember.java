package hong.CashGuard.domain.group.domain.member;

import hong.CashGuard.domain.code.GroupJoinType;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.domain
 * fileName       : CgGroupMember
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 하위 사용자 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupMember {

    private Long userUid;
    private Long groupUid;
    private String joinType;    // GroupJoinType.INVITE, APPLY
    private String joinDt;
    private String isApproved;
    private String approvedDt;

    /**
     * @method      CgGroupMember 생성자 1
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 사용자 추가 생성자 (joinType: APPLY - 신청)
    **/
    public CgGroupMember(CgGroupMemberSave request) {
        this.userUid = request.getUserUid();
        this.groupUid = request.getGroupUid();
        this.joinType = GroupJoinType.APPLY.name();
        this.isApproved = "N";
    }

    /**
     * @method      CgGroupMember 생성자 2
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 사용자 승인 생성자
    **/
    public CgGroupMember(Long userUid, Long groupUid, String isApproved) {
        this.userUid = userUid;
        this.groupUid = groupUid;
        this.isApproved = isApproved;
    }

    /**
     * @method      CgGroupMember 생성자 3
     * @author      work
     * @date        2025-04-03
     * @deacription 초대받은 사용자 추가하기
    **/
    public CgGroupMember(Long userUid, Long groupUid) {
        this.userUid = userUid;
        this.groupUid = groupUid;
        this.joinType = GroupJoinType.INVITE.name();;
        this.isApproved = "Y";
    }
}
