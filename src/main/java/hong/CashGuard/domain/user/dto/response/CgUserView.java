package hong.CashGuard.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.user.dto.response
 * fileName       : CgUserView
 * author         : work
 * date           : 2025-03-27
 * description    : 유저 관련 정보들 응답 DTO > 유저에 대한 다양한 정보가 담긴다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgUserView {

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
}
