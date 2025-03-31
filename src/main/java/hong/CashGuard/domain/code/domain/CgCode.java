package hong.CashGuard.domain.code.domain;

import hong.CashGuard.domain.code.dto.request.CgCodeSave;
import hong.CashGuard.global.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.code.domain
 * fileName       : CgCode
 * author         : home
 * date           : 2025-03-31
 * description    : CG_CODE 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCode extends AuditBean {

    private Long uid;
    private String upperCode;
    private String code;
    private String codeNm;
    private String useAt;

    public CgCode(CgCodeSave request) {
        this.upperCode = null;
        this.code = request.getCode();
        this.codeNm = request.getCodeNm();
        this.useAt = request.getUseAt();
    }
}