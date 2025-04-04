package hong.CashGuard.domain.board.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.board.dto.response
 * fileName       : CgBoardList
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoardList extends AuditMetaData {

    private Long bbsUid;
    private String bbsNm;
    private String bbsTpCd;
    private String bbsTpCdNm;
    private Long groupUid;
    private String groupNm;
    private Long boardUid;
    private String title;
    private String content;
    private Long fileUid;
    private Long thumbUid;
    private String deleteAt;
}
