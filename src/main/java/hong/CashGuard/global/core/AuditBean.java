package hong.CashGuard.global.core;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.global.core
 * fileName       : AuditBean
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
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
