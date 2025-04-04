package hong.CashGuard.domain.comment.service;

import hong.CashGuard.domain.comment.dto.request.CgBoardCommentChange;
import hong.CashGuard.domain.comment.dto.request.CgBoardCommentSave;
import hong.CashGuard.domain.comment.dto.response.CgBoardCommentList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.comment.service
 * fileName       : CgBoardCommentRestController
 * author         : work
 * date           : 2025-04-04
 * description    : 댓글 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/comment")
public class CgBoardCommentRestController {

    private final CgBoardCommentService service;

    /**
     *
     * 댓글 저장 (최상위 댓글)
     *
     * @api         [POST] /cguard/api/comment/{board}
     * @author      work
     * @date        2025-04-04
    **/
    @PostMapping("/{board}")
    public ResponseEntity saveComment(@PathVariable("board") Long board, @RequestBody @Valid CgBoardCommentSave request) {
        service.saveComment(board, request, null);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 저장 (자식 댓글) -> {uid} 하위 댓글
     *
     * @api         [POST] /cguard/api/comment/{board}/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @PostMapping("/{board}/{uid}")
    public ResponseEntity saveChildComment(@PathVariable("board") Long board, @PathVariable("uid") Long uid,
                                           @RequestBody @Valid CgBoardCommentSave request) {
        service.saveComment(board, request, uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 수정
     *
     * @api         [PUT] /cguard/api/comment/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeComment(@PathVariable("uid") Long uid, @RequestBody @Valid CgBoardCommentChange request) {
        service.changeComment(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 삭제
     *
     * @api         [DELETE] /cguard/api/comment/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteComment(@PathVariable("uid") Long uid) {
        service.deleteComment(uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 트리 구조 반환
     *
     * @api         [GET] /cguard/api/comment/tree/{board}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/tree/{board}")
    public ResponseEntity findAllCommentTree(@PathVariable("board") Long board) {
        List<CgBoardCommentList> commentTree = service.findAllCommentTree(board);
        return ResponseEntity.ok(commentTree);
    }
}
