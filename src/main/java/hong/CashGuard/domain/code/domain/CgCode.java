package hong.CashGuard.domain.code.domain;

import hong.CashGuard.domain.code.dto.request.CgCodeObj;
import hong.CashGuard.domain.code.dto.request.CgCodeSave;
import hong.CashGuard.global.bean.AuditBean;
import hong.CashGuard.global.util.StringUtil;
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

    /**
     * @method      CgCode 생성자 1
     * @author      work
     * @date        2025-04-02
     * @deacription 부모 코드 저장
    **/
    public CgCode(CgCodeSave request) {
        this.upperCode = null;
        this.code = request.getCode();
        this.codeNm = request.getCodeNm();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      CgCode 생성자 2
     * @author      work
     * @date        2025-04-02
     * @deacription 자식 코드 저장
    **/
    public CgCode(String parentCode, CgCodeObj request) {
        this.upperCode = parentCode;
        this.code = request.getCode();
        this.codeNm = request.getCodeNm();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      CgCode 생성자 3
     * @author      work
     * @date        2025-04-02
     * @deacription 부모/자식 코드 수정
    **/
    public CgCode changeCode(String code, CgCodeObj request) {
        this.code = code;
        this.upperCode = StringUtil.getOrDefault(request.getUpperCode(), this.upperCode);
        this.codeNm = StringUtil.getOrDefault(request.getCodeNm(), this.codeNm);
        this.useAt = StringUtil.getOrDefault(request.getUseAt(), this.useAt);
        return this;
    }
}