package hong.CashGuard.global.bean.audit;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.global.bean.audit
 * fileName       : AuditInfo
 * author         : work
 * date           : 2025-04-04
 * description    : {regUid, regDt, regNm, updtUid, updtDt, updtNm} 응답 공통 묶음용
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter
public class AuditMetaData {

    private Long regUid;
    private String regDt;
    private String regNm;
    private Long updtUid;
    private String updtDt;
    private String updtNm;
}
