package hong.CashGuard.domain.trans.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.request
 * fileName       : CgTransChange
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 수정 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTransChange {

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
