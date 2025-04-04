package hong.CashGuard.domain.category.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
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
 * 2025-04-04        work       {regUid, regDt, regNm, updtUid, updtDt, updtNm} => AuditMetaData 으로 빼기
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCategoryList extends AuditMetaData {

    private Long uid;
    private String categoryCd;
    private String categoryNm;
    private String categoryTp;
    private String categoryTpNm;
    private boolean categoryTpIsAdd;
    private String useAt;

}
