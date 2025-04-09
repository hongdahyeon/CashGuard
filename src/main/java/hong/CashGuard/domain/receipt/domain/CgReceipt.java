package hong.CashGuard.domain.receipt.domain;

import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.receipt.domain
 * fileName       : CgReceipt
 * author         : work
 * date           : 2025-04-08
 * description    : 영수증 정보 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgReceipt extends AuditBean {

    private Long uid;
    private Long userUid;
    private Long imageUid;
    private String scannedData;

    /**
     * @method      CgReceipt 생성자 1
     * @author      work
     * @date        2025-04-09
     * @deacription 영수증 인식 정보 저장용 생성자
    **/
    public CgReceipt(String extractText, Long userUid, Long imageUid) {
        this.scannedData = extractText;
        this.userUid = userUid;
        this.imageUid = imageUid;
    }
}