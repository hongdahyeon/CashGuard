package hong.CashGuard.domain.group.dto.response.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.group.dto.response.category
 * fileName       : CgGroupCategoryList
 * author         : work
 * date           : 2025-04-03
 * description    : 그룹 하위 카테고리 목록 조회 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgGroupCategoryList {

    private Long categoryUid;
    private String categoryCd;
    private String categoryNm;
    private String categoryTp;
    private String categoryTpNm;
    private boolean categoryTpIsAdd;

}
