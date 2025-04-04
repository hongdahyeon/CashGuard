package hong.CashGuard.domain.group.domain.category;

import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.domain.category
 * fileName       : CgGroupCategory
 * author         : work
 * date           : 2025-04-03
 * description    : 그룹 하위 카테고리 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupCategory extends AuditBean {

    private Long groupUid;
    private Long categoryUid;

    /**
     * @method      CgGroupCategory 생성자 1
     * @author      work
     * @date        2025-04-03
     * @deacription 그룹 하위 카테고리 추가 생성자
    **/
    public CgGroupCategory(Long groupUid, Long categoryUid) {
        this.groupUid = groupUid;
        this.categoryUid = categoryUid;
    }
}
