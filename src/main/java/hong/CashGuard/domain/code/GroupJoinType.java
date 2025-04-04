package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : GroupJoinType
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 하위 사용자들 참여 방법
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       그룹 멤버 테이블에 대표자도 담게 되면서, 대표자 타입 추가
 */

@Getter
public enum GroupJoinType {

    EXPONENT("대표자"),
    INVITE("초대"),
    APPLY("신청");

    private String description;

    GroupJoinType(String description){
        this.description = description;
    }
}
