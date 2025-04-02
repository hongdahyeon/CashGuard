package hong.CashGuard.domain.category.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.category.dto.response
 * fileName       : CgCategoryList
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 목록 조회 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCategoryList {

    private Long uid;
    private String categoryCd;
    private String categoryNm;
    private String categoryTp;
    private String categoryTpNm;
    private boolean categoryTpIsAdd;
    private String useAt;
    private Long regUid;
    private String regDt;
    private String regNm;
    private String updtUid;
    private String updtDt;
    private String updtNm;
}
