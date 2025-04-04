package hong.CashGuard.domain.board.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.board.dto.request
 * fileName       : CgBoardParam
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 목록 조회 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @Setter
public class CgBoardParam {

    private String title;

    private String regNm;

    private Long groupUid;
    private Long bbsUid;

    public void setInfo(Long groupUid, Long bbsUid) {
        this.groupUid = groupUid;
        this.bbsUid = bbsUid;
    }
}
