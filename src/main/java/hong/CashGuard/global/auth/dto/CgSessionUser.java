package hong.CashGuard.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hong.CashGuard.domain.user.dto.response.CgUserView;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * packageName    : hong.CashGuard.global.auth.dto
 * fileName       : CgSessionUser
 * author         : work
 * date           : 2025-03-27
 * description    : 세션유저로 활용될 VO
 *                  => {userId} 값을 기준으로 비교
 *                  => {callSuper = false} 부모 클래스의 equals(), hashCode() 호출 안하도록
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Getter
@EqualsAndHashCode(of = "userId", callSuper = false)
public class CgSessionUser implements Serializable {

    private Long uid;
    private String userId;
    @JsonIgnore
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

    public CgSessionUser(CgUserView user) {
        this.uid = user.getUid();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userNm = user.getUserNm();
        this.userTel = user.getUserTel();
        this.userEmail = user.getUserEmail();
        this.lastConnDt = user.getLastConnDt();
        this.lastPasswdChngDt = user.getLastPasswdChngDt();
        this.profileUid = user.getProfileUid();
        this.isEnable = user.getIsEnable();
        this.pwdFailCnt = user.getPwdFailCnt();
        this.isLocked = user.getIsLocked();
        this.role = user.getRole();
    }
}
