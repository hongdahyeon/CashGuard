package hong.CashGuard.domain.group.dto.response;

import hong.CashGuard.domain.group.dto.response.category.CgGroupCategoryList;
import hong.CashGuard.domain.group.dto.response.member.CgGroupMemberList;
import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.group.dto.response
 * fileName       : CgGroupAndMemberAndCategoryList
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 정보 응답 DTO
 *                  + 각각의 그룹 하위 사용자 리스트 정보 조회
 *                  + 각각의 그룹에 대한 활성화 카테고리 목록 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       {regUid, regDt, regNm, updtUid, updtDt, updtNm} => AuditMetaData 으로 빼기
 * 2025-04-04        work       uid->groupUid, userUid->exponentUid, userNm->exponentNm
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupAndMemberAndCategoryList extends AuditMetaData {

    private Long groupUid;
    private Long exponentUid;
    private String exponentNm;
    private String groupNm;
    private String isPrivate;
    private int approvedMember;
    private int totalMember;
    private boolean isActive;
    private List<CgGroupMemberList> members = new ArrayList<>();
    private List<CgGroupCategoryList> categories = new ArrayList<>();

    public void setMembers(List<CgGroupMemberList> members) {
        this.members = members;
    }
    public void setCategories(List<CgGroupCategoryList> categories) {
        this.categories = categories;
    }
}
