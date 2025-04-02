package hong.CashGuard.domain.category.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.category.dto.request
 * fileName       : CgCategoryChange
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 수정 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCategoryChange {

    @NotBlank
    private String categoryNm;

    @YorN(allowNull = false)
    private String useAt;
}
