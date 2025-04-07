package hong.CashGuard.domain.trans.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : hong.CashGuard.domain.trans.dto.request
 * fileName       : CgTransCheck
 * author         : work
 * date           : 2025-04-07
 * description    : 수입/지출 초과 여부에 대한 체크
 *                  -> {userUid}가 {ledgerUid}에 대해서 작성해둔 예산 목표 금액을 체크한다.
 *                     이때 {start} ~ {end} 범위로 체크하게 된다.
 *                     {transCd} 값은 "INCOME" 혹은 "EXPENSE" 중에 하나가 된다.
 *                     둘 중에 하나에 대해서 해당 날짜 범위 내의
 *                     =>> INCOME : 수입의 합
 *                     =>> EXPENSE : 지출의 합
 *                     값을 구해준다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgTransCheck {

    private Long userUid;
    private Long ledgerUid;
    private String transCd;
    private LocalDateTime start;
    private LocalDateTime end;

   @Builder(builderMethodName = "checkTransBuilder")
    public CgTransCheck(Long userUid, Long ledgerUid, String transCd, LocalDateTime start, LocalDateTime end) {
        this.userUid = userUid;
        this.ledgerUid = ledgerUid;
        this.transCd = transCd;
        this.start = start;
        this.end = end;
    }
}
