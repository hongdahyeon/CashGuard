package hong.CashGuard.global.bean;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.global.bean
 * fileName       : AuditBean
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-03-31        home       상위 패키지명 변경 (core->bean)
 */


@Getter
public class AuditBean {

    private Long regUid;
    private Long updtUid;

    public void setRegUid(Long regUid) {
        this.regUid = regUid;
    }
    public void setUpdtUid(Long updtUid) {
        this.updtUid = updtUid;
    }
}
