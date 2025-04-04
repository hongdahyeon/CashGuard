package hong.CashGuard.domain.comment.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.comment.dto.response
 * fileName       : CgBoardCommentList
 * author         : work
 * date           : 2025-04-04
 * description    : 댓글 목록 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoardCommentList extends AuditMetaData {

    private Long uid;
    private Long upperUid;
    private String content;

    private List<CgBoardCommentList> children = new ArrayList<>();
}
