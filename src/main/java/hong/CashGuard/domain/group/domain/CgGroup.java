package hong.CashGuard.domain.group.domain;

import hong.CashGuard.domain.group.dto.request.CgGroupChange;
import hong.CashGuard.domain.group.dto.request.CgGroupSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.domain
 * fileName       : CgGroup
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroup extends AuditBean {

    private Long uid;
    private Long userUid;
    private String groupNm;
    private String isPrivate;

    /**
     * @method      CgGroup 생성자 1
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 추가 생성자
    **/
    public CgGroup(Long loginUserUid, CgGroupSave request) {
        this.userUid = loginUserUid;
        this.groupNm = request.getGroupNm();
        this.isPrivate = request.getIsPrivate();
    }

    /**
     * @method      CgGroup 생성자 2
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 수정 생성자
    **/
    public CgGroup changeGroup(Long uid, CgGroupChange request) {
        this.uid = uid;
        this.groupNm = StringUtil.getOrDefault(request.getGroupNm(), this.groupNm);
        this.isPrivate = StringUtil.getOrDefault(request.getIsPrivate(), this.isPrivate);
        return this;
    }
}
