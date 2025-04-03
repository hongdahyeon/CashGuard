package hong.CashGuard.domain.ledger.service;

import hong.CashGuard.domain.ledger.dto.request.CgLedgerChange;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerParam;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerSave;
import hong.CashGuard.domain.ledger.dto.response.CgLedgerList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.ledger.service
 * fileName       : CgLedgerRestController
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/ledger")
public class CgLedgerRestController {

    private final CgLedgerService service;

    /**
     *
     * 가계부 생성
     *
     * @api         [POST] /cguard/api/ledger
     * @author      work
     * @date        2025-04-03
    **/
    @PostMapping
    public ResponseEntity saveLedger(@RequestBody @Valid CgLedgerSave request) {
        service.saveLedger(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 가계부 정보 수정
     *
     * @api         [PUT] /cguard/api/ledger/{uid}
     * @author      work
     * @date        2025-04-03
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeLedger(@PathVariable("uid") Long uid, @RequestBody @Valid CgLedgerChange request) {
        service.changeLedger(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 가계부 삭제
     *
     * @api         [DELETE] /cguard/api/ledger/{uid}
     * @author      work
     * @date        2025-04-03
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteLedger(@PathVariable("uid") Long uid) {
        service.deleteLedger(uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 가계부 목록 조회 (페이징)
     * > 로그인한 사용자 본인 것만 조회 가능
     *
     * @api         [GET] /cguard/api/ledger/page
     * @author      work
     * @date        2025-04-03
    **/
    @GetMapping("/page")
    public ResponseEntity findAllLedgerUserPage(@Valid CgLedgerParam param, Pageable pageable) {
        Page<CgLedgerList> allLedgerPage = service.findAllLedgerUserPage(param, pageable);
        return ResponseEntity.ok(allLedgerPage);
    }

    /**
     *
     * 가계부 목록 조회 (리스트)
     * > 로그인한 사용자 본인 것만 조회 가능
     *
     * @api         [GET] /cguard/api/ledger/list
     * @author      work
     * @date        2025-04-03
    **/
    @GetMapping("/list")
    public ResponseEntity findAllLedgerUserList(@Valid CgLedgerParam param) {
        List<CgLedgerList> allLedgerUserList = service.findAllLedgerUserList(param);
        return ResponseEntity.ok(allLedgerUserList);
    }
}
