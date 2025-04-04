package hong.CashGuard.domain.bbs.service;

import hong.CashGuard.domain.bbs.dto.request.CgBbsChange;
import hong.CashGuard.domain.bbs.dto.request.CgBbsParam;
import hong.CashGuard.domain.bbs.dto.request.CgBbsSave;
import hong.CashGuard.domain.bbs.dto.response.CgBbsList;
import hong.CashGuard.domain.bbs.dto.response.CgBbsView;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.bbs.service
 * fileName       : CgBbsRestController
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/bbs/manager")
public class CgBbsRestController {

    private final CgBbsService service;

    /**
     *
     * 게시판 저장
     *
     * @api         [POST] /cguard/api/bbs/manager
     * @author      work
     * @date        2025-04-04
    **/
    @PostMapping
    public ResponseEntity saveBbs(@RequestBody @Valid CgBbsSave request){
        service.saveBbs(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시판 수정
     *
     * @api         [PUT] /cguard/api/bbs/manager/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBbs(@PathVariable("uid") Long uid, @RequestBody @Valid CgBbsChange request) {
        service.changeBbs(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시판 단건 조회
     *
     * @api         [GET] /cguard/api/bbs/manager/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findBbsByUid(@PathVariable("uid") Long uid) {
        CgBbsView bbsByUid = service.findBbsByUid(uid);
        return ResponseEntity.ok(bbsByUid);
    }

    /**
     *
     * 게시판 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/bbs/manager/page
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/page")
    public ResponseEntity findAllPage(@Valid CgBbsParam param, Pageable pageable) {
        Page<CgBbsList> allPage = service.findAllPage(param, pageable);
        return ResponseEntity.ok(allPage);
    }

    /**
     *
     * 게시판 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/bbs/manager/list
     * @author      work
     * @date        2025-04-04
     **/
    @GetMapping("/list")
    public ResponseEntity findAllList(@Valid CgBbsParam param) {
        List<CgBbsList> allList = service.findAllList(param);
        return ResponseEntity.ok(allList);
    }

    /**
     *
     * 게시판 단건 삭제
     *
     * @api         [DELETE] /cguard/api/bbs/manager/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBbs(@PathVariable("uid") Long uid) {
        service.deleteBbs(uid);
        return ResponseEntity.ok().build();
    }

}
