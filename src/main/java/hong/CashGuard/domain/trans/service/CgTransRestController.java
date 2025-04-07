package hong.CashGuard.domain.trans.service;

import hong.CashGuard.domain.code.BudgetTrans;
import hong.CashGuard.domain.code.Category;
import hong.CashGuard.domain.trans.dto.request.CgTransChange;
import hong.CashGuard.domain.trans.dto.request.CgTransParam;
import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.domain.trans.dto.response.CgTransList;
import hong.CashGuard.domain.trans.dto.response.CgTransView;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.trans.service
 * fileName       : CgTransRestController
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       수입/지출 수정, 단건 조회, 페이징/리스트 조회, 단건 삭제 API 추가
 *                              => 페이징/리스트의 경우 로그인한 본인것만 조회가능
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/trans")
public class CgTransRestController {

    private final CgTransService service;

    /**
     *
     * 수입/지출 저장
     *
     * @api         [POST] /cguard/api/trans
     * @author      work
     * @date        2025-04-06
    **/
    @PostMapping
    public ResponseEntity saveTrans(@RequestBody @Valid CgTransSave request) {
        if( !BudgetTrans.isValidTrans(request.getTransCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        service.saveTrans(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 수입/지출 수정
     *
     * @api         [PUT] /cguard/api/trans/{uid}
     * @author      work
     * @date        2025-04-07
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeTrans(@PathVariable("uid") Long uid, @RequestBody @Valid CgTransChange request) {
        if( !BudgetTrans.isValidTrans(request.getTransCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        service.changeTrans(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 수입/지출 단건 조회
     *
     * @api         [GET] /cguard/api/trans/{uid}
     * @author      work
     * @date        2025-04-07
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findTransByUid(@PathVariable("uid") Long uid) {
        CgTransView transByUid = service.findTransByUid(uid);
        return ResponseEntity.ok(transByUid);
    }

    /**
     *
     * 현재 로그인한 사용자의 수입/지출 내역 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/trans/my/page
     * @author      work
     * @date        2025-04-07
    **/
    @GetMapping("/my/page")
    public ResponseEntity findAllMyPage(@Valid CgTransParam param, Pageable pageable) {
        if( (param.getTransCd() != null) && !BudgetTrans.isValidTrans(param.getTransCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        if( (param.getCategoryTp() != null) && !Category.isValidCategory(param.getCategoryTp()) ) {
            throw new CGException("유효하지 않은 카테고리 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        Page<CgTransList> allMyPage = service.findAllMyPage(param, pageable);
        return ResponseEntity.ok(allMyPage);
    }

    /**
     *
     * 현재 로그인한 사용자의 수입/지출 내역 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/trans/my/list
     * @author      work
     * @date        2025-04-07
    **/
    @GetMapping("/my/list")
    public ResponseEntity findAllMyList(@Valid CgTransParam param) {
        if( (param.getTransCd() != null) && !BudgetTrans.isValidTrans(param.getTransCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        if( (param.getCategoryTp() != null) && !Category.isValidCategory(param.getCategoryTp()) ) {
            throw new CGException("유효하지 않은 카테고리 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        List<CgTransList> allMyList = service.findAllMyList(param);
        return ResponseEntity.ok(allMyList);
    }

    /**
     *
     * 수입/지출 내역 단건 삭제
     *
     * @api         [DELETE] /cguard/api/trans/{uid}
     * @author      work
     * @date        2025-04-07
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteTrans(@PathVariable("uid") Long uid) {
        service.deleteTrans(uid);
        return ResponseEntity.ok().build();
    }
}
