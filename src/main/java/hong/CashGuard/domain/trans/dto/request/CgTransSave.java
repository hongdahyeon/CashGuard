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
    private Long ledgerUid;         // 가계부 UID

    @NotNull
    private Long categoryUid;       // 카테고리 UID

    @NotBlank
    private String transCd;         // 수입/지출 유형 코드

    @NotNull
    private long amount;            // 금액

    private String description;     // 설명

    @NotBlank
    private String transDate;       // 거래일자

    private String note;            // 기타 노트
}
