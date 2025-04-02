package hong.CashGuard.domain.category.dto.request;

import hong.CashGuard.global.annotation.YorN;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.category.dto.request
 * fileName       : CgCategoryParam
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 목록 조회 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @Setter
public class CgCategoryParam {

    @YorN
    private String useAt;

    private String categoryNm;

    private String categoryTp;
}
