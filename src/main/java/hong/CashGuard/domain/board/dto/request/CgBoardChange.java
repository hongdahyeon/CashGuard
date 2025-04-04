package hong.CashGuard.domain.board.dto.request;

import hong.CashGuard.global.bean.file.FileBean;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.board.dto.request
 * fileName       : CgBoardChange
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBoardChange extends FileBean {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long fileUid;

    private Long thumbUid;
}
