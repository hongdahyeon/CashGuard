package hong.CashGuard.domain.code.dto.request;

import hong.CashGuard.global.annotation.YorN;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.code.dto.request
 * fileName       : CgCodeParam
 * author         : home
 * date           : 2025-03-31
 * description    : 코드 페이징 조회 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Getter @Setter
public class CgCodeParam {

    private String code;
    private String codeNm;
    @YorN
    public String useAt;

}