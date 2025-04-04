package hong.CashGuard.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.comment.dto.request
 * fileName       : CgBoardCommentSave
 * author         : work
 * date           : 2025-04-04
 * description    : 댓글 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoardCommentSave {

    @NotBlank
    private String content;
}
