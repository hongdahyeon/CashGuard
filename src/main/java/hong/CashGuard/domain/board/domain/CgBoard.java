package hong.CashGuard.domain.board.domain;

import hong.CashGuard.domain.board.dto.request.CgBoardChange;
import hong.CashGuard.domain.board.dto.request.CgBoardSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.board.domain
 * fileName       : CgBoard
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoard extends AuditBean {

    private Long uid;
    private Long bbsUid;
    private Long groupUid;
    private String title;
    private String content;
    private Long fileUid;
    private Long thumbUid;
    private String deleteAt;

    /**
     * @method      CgBoard 생성자 1
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 저장 생성자
    **/
    public CgBoard(Long bbsUid, Long groupUid, CgBoardSave request, Long fileUid, Long thumbUid) {
        this.bbsUid = bbsUid;
        this.groupUid = groupUid;
        this.title = request.getTitle();
        this.content = request.getContent();
        this.fileUid = fileUid;
        this.thumbUid = thumbUid;
    }

    /**
     * @method      CgBoard 생성자 2
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 수정 생성자
    **/
    public CgBoard(Long boardUid, CgBoardChange request, Long fileUid, Long thumbUid) {
        this.uid = boardUid;
        this.title = request.getTitle();
        this.content = request.getContent();
        this.fileUid = fileUid;
        this.thumbUid = thumbUid;
    }

    /**
     * @method      CgBoard 생성자 3
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 삭제 생성자
    **/
    public CgBoard(Long boardUid) {
        this.uid = boardUid;
    }
}
