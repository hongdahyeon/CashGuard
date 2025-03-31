package hong.CashGuard.domain.code.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.code.dto.response
 * fileName       : CgCodeList
 * author         : home
 * date           : 2025-03-31
 * description    : 코드 목록 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCodeList {

    private Long uid;
    private String upperCode;
    private String code;
    private String codeNm;
    private String useAt;
    private Long regUid;
    private String regNm;
    private String regDt;
    private Long updtUid;
    private String updtNm;
    private String updtDt;
}
