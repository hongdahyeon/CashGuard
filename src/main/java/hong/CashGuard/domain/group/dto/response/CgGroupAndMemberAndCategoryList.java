package hong.CashGuard.domain.group.dto.response;

import hong.CashGuard.domain.group.dto.response.category.CgGroupCategoryList;
import hong.CashGuard.domain.group.dto.response.member.CgGroupMemberList;
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
 * description    : 그룹 정보 + 각각의 그룹 하위 사용자 리스트 정보 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupAndMemberAndCategoryList {

    private Long uid;
    private Long userUid;
    private String userNm;
    private String groupNm;
    private String isPrivate;
    private int approvedMember;
    private int totalMember;
    private boolean isActive;
    private Long regUid;
    private String regDt;
    private String regNm;
    private Long updtUid;
    private String updtDt;
    private String updtNm;
    private List<CgGroupMemberList> members = new ArrayList<>();
    private List<CgGroupCategoryList> categories = new ArrayList<>();

    public void setMembers(List<CgGroupMemberList> members) {
        this.members = members;
    }
    public void setCategories(List<CgGroupCategoryList> categories) {
        this.categories = categories;
    }
}
