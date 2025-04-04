package hong.CashGuard.domain.board.service;

import hong.CashGuard.domain.board.dto.request.CgBoardChange;
import hong.CashGuard.domain.board.dto.request.CgBoardParam;
import hong.CashGuard.domain.board.dto.request.CgBoardSave;
import hong.CashGuard.domain.board.dto.response.CgBoardList;
import hong.CashGuard.domain.board.dto.response.CgBoardView;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.board.service
 * fileName       : CgBoardRestController
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/board")
public class CgBoardRestController {

    private final CgBoardService service;

    /**
     *
     * 게시글 저장
     *
     * @api         [POST] /cguard/api/board/{group}/{bbs}
     * @author      work
     * @date        2025-04-04
    **/
    @PostMapping("/{group}/{bbs}")
    public ResponseEntity saveBoard(@PathVariable("group") Long group, @PathVariable("bbs") Long bbs,
                                    @RequestBody @Valid CgBoardSave request) {
        service.saveBoard(group, bbs, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 수정
     *
     * @api         [PUT] /cguard/api/board/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBoard(@PathVariable("uid") Long uid, @RequestBody @Valid CgBoardChange request) {
        service.changeBoard(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 단건 삭제
     *
     * @api         [DELETE] /cguard/api/board/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBoard(@PathVariable("uid") Long uid) {
        service.deleteBoard(uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * {groupUid, bbsUid} 하위 게시글 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/board/{group}/{bbs}/page
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/{group}/{bbs}/page")
    public ResponseEntity findAllPageByBbsAndGroup(@PathVariable("group") Long group, @PathVariable("bbs") Long bbs,
                                                   @Valid CgBoardParam param, Pageable pageable) {
        Page<CgBoardList> page = service.findAllPageByBbsAndGroup(group, bbs, param, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * {groupUid, bbsUid} 하위 게시글 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/board/{group}/{bbs}/page
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/{group}/{bbs}/list")
    public ResponseEntity findAllPageByBbsAndGroup(@PathVariable("group") Long group, @PathVariable("bbs") Long bbs, @Valid CgBoardParam param) {
        List<CgBoardList> list = service.findAllListByBbsAndGroup(group, bbs, param);
        return ResponseEntity.ok(list);
    }

    /**
     *
     * 게시글 단건 조회 (with. file, thumbnail)
     *
     * @api         [GET] /cguard/api/board/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findBoardByUid(@PathVariable("uid") Long uid) {
        CgBoardView boardByUid = service.findBoardByUid(uid);
        return ResponseEntity.ok(boardByUid);
    }
}
