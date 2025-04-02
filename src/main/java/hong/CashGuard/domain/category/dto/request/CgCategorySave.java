package hong.CashGuard.domain.category.dto.request;

import hong.CashGuard.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.category.dto.request
 * fileName       : CgCategorySave
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 저장 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCategorySave {

    @NotBlank
    private String categoryCd;

    @NotBlank
    private String categoryNm;

    @NotBlank
    private String categoryTp;

    @YorN(allowNull = false)
    private String useAt;
}
