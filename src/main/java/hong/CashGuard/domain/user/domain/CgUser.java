package hong.CashGuard.domain.user.domain;

import hong.CashGuard.domain.user.dto.request.CgUserChange;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.global.bean.AuditBean;
import hong.CashGuard.global.util.StringUtil;
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

    /**
     * @method      CgUser 생성자 1
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 저장용 생성자
    **/
    public CgUser(CgUserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userNm = request.getUserNm();
        this.userTel = request.getUserTel();
        this.userEmail = request.getUserEmail();
        this.role = request.getRole();
    }

   /**
    * @method      CgUser 생성자 2
    * @author      work
    * @date        2025-04-01
    * @deacription 사용자 수정용 생성자
   **/
    public CgUser changeUser(Long uid, CgUserChange request, String encodePassword) {
        this.uid = uid;
        this.password = StringUtil.getOrDefault(encodePassword, null);
        this.userNm = StringUtil.getOrDefault(request.getUserNm(), this.userNm);
        this.userTel = StringUtil.getOrDefault(request.getUserTel(), this.userTel);
        this.userEmail = StringUtil.getOrDefault(request.getUserEmail(), this.userEmail);
        return this;
    }

    /**
     * @method      CgUser 생성자 3
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 비밀번호 변경용 생성자
    **/
    public CgUser(String userId, String encodePassword) {
        this.userId = userId;
        this.password = StringUtil.getOrDefault(encodePassword, null);
    }

    /**
     * @method      CgUser 생성자 4
     * @author      work
     * @date        2025-04-01
     * @deacription * 사용자 잠김 풀기용 생성자
    **/
    public CgUser(Long uid) {
        this.uid = uid;
    }

    /**
     * @method      CgUser 생성자 5
     * @author      work
     * @date        2025-04-01
     * @deacription * 사용자 활성화/비활성화 수정용 생성자
    **/
    public CgUser(Long uid, String isEnable) {
        this.uid = uid;
        this.isEnable = isEnable;
    }
}
