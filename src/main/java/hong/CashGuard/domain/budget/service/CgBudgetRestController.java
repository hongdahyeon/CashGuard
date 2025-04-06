package hong.CashGuard.domain.budget.service;

import hong.CashGuard.domain.budget.dto.request.CgBudgetChange;
import hong.CashGuard.domain.budget.dto.request.CgBudgetParam;
import hong.CashGuard.domain.budget.dto.request.CgBudgetSave;
import hong.CashGuard.domain.budget.dto.response.CgBudgetList;
import hong.CashGuard.domain.budget.dto.response.CgBudgetView;
import hong.CashGuard.domain.code.BudgetPeriod;
import hong.CashGuard.domain.code.BudgetTrans;
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
 * packageName    : hong.CashGuard.domain.budget.service
 * fileName       : CgBudgetRestController
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/budget")
public class CgBudgetRestController {

    private final CgBudgetService service;

    /**
     *
     * 예산 목표 저장
     *
     * @api         [POST] /cguard/api/budget
     * @author      work
     * @date        2025-04-06
    **/
    @PostMapping
    public ResponseEntity saveBudget(@RequestBody @Valid CgBudgetSave request){
        if( !BudgetPeriod.isValidPeriod(request.getPeriodType()) ) {
            throw new CGException("유효하지 않은 예산 목표 기간 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        if( !BudgetTrans.isValidTrans(request.getTransTpCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        service.saveBudget(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 예산 목표 수정
     *
     * @api         [PUT] /cguard/api/budget/{uid}
     * @author      work
     * @date        2025-04-06
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBudget(@PathVariable("uid") Long uid, @RequestBody @Valid CgBudgetChange request) {
        if( !BudgetPeriod.isValidPeriod(request.getPeriodType()) ) {
            throw new CGException("유효하지 않은 예산 목표 기간 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        if( !BudgetTrans.isValidTrans(request.getTransTpCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        service.changeBudget(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 예산 목표 단건 조회
     *
     * @api         [GET] /cguard/api/budget/{uid}
     * @author      work
     * @date        2025-04-06
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findBudgetByUid(@PathVariable("uid") Long uid) {
        CgBudgetView budgetByUid = service.findBudgetByUid(uid);
        return ResponseEntity.ok(budgetByUid);
    }

    /**
     *
     * 예산 목표 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/budget/page
     * @author      work
     * @date        2025-04-06
    **/
    @GetMapping("/page")
    public ResponseEntity findAllPage(@Valid CgBudgetParam param, Pageable pageable) {
        Page<CgBudgetList> allPage = service.findAllPage(param, pageable);
        return ResponseEntity.ok(allPage);
    }


    /**
     *
     * 예산 목표 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/budget/list
     * @author      work
     * @date        2025-04-06
    **/
    @GetMapping("/list")
    public ResponseEntity findAllList(@Valid CgBudgetParam param) {
        List<CgBudgetList> allList = service.findAllList(param);
        return ResponseEntity.ok(allList);
    }


    /**
     *
     * 예산 목표 삭제
     *
     * @api         [DELETE] /cguard/api/budget/{uid}
     * @author      work
     * @date        2025-04-06
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBudget(@PathVariable("uid") Long uid) {
        service.deleteBudget(uid);
        return ResponseEntity.ok().build();
    }

}
