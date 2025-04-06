package hong.CashGuard.domain.trans.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.request
 * fileName       : CgTransSave
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 저장 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTransSave {

    @NotNull
    private Long ledgerUid;

    @NotNull
    private Long categoryUid;

    @NotBlank
    private String transCd;

    @NotNull
    private long amount;

    private String description;

    @NotBlank
    private String transDate;

    private String note;
}
