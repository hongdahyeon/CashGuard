package hong.CashGuard.domain.user.domain;

import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.global.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.user.domain
 * fileName       : CgUser
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgUser extends AuditBean {

    private Long uid;
    private String userId;
    private String password;
    private String userNm;
    private String userTel;
    private String userEmail;
    private String lastConnDt;
    private String lastPasswdChngDt;
    private Long profileUid;
    private String isEnable;
    private Integer pwdFailCnt;
    private String isLocked;
    private String role;

    public CgUser(CgUserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userNm = request.getUserNm();
        this.userTel = request.getUserTel();
        this.userEmail = request.getUserEmail();
        this.role = request.getRole();
    }
}
