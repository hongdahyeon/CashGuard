package hong.CashGuard.domain.bbs.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.bbs.dto.response
 * fileName       : CgBbsView
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 단건 조회용 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBbsView extends AuditMetaData {

    private Long uid;
    private String bbsTpCd;
    private String bbsTpCdNm;
    private String bbsNm;
    private String useAt;
    private String deleteAt;
}
