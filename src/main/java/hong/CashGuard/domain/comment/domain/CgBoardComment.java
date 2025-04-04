package hong.CashGuard.domain.comment.domain;

import hong.CashGuard.domain.comment.dto.request.CgBoardCommentChange;
import hong.CashGuard.domain.comment.dto.request.CgBoardCommentSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.comment.domain
 * fileName       : CgBoardComment
 * author         : work
 * date           : 2025-04-04
 * description    : 댓글 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoardComment extends AuditBean {

    private Long uid;
    private Long boardUid;
    private Long upperUid;
    private String content;
    private String deleteAt;

    /**
     * @method      CgBoardComment 생성자 1
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 저장 생성자
    **/
    public CgBoardComment(Long boardUid, CgBoardCommentSave request, Long upperUid) {
        this.boardUid = boardUid;
        this.content = request.getContent();
        this.upperUid = upperUid;
    }

    /**
     * @method      CgBoardComment 생성자 2
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 수정 생성자
    **/
    public CgBoardComment(Long commentUid, CgBoardCommentChange request) {
        this.uid = commentUid;
        this.content = request.getContent();
    }

    /**
     * @method      CgBoardComment 생성자 3
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 삭제 생성자
    **/
    public CgBoardComment(Long commentUid) {
        this.uid = commentUid;
    }
}
