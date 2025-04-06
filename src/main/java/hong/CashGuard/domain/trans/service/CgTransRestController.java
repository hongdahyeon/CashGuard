package hong.CashGuard.domain.trans.service;

import hong.CashGuard.domain.code.BudgetTrans;
import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.global.exception.CGException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
