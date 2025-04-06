package hong.CashGuard.domain.trans.service;

import hong.CashGuard.domain.calendar.dto.request.CgCalendarSave;
import hong.CashGuard.domain.calendar.service.CgCalendarService;
import hong.CashGuard.domain.category.service.CgCategoryService;
import hong.CashGuard.domain.ledger.service.CgLedgerService;
import hong.CashGuard.domain.trans.domain.CgTrans;
import hong.CashGuard.domain.trans.domain.CgTransMapper;
import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.trans.service
 * fileName       : CgTransService
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CgTransService {

    private final CgTransMapper mapper;
    private final CgLedgerService ledgerService;
    private final CgCategoryService categoryService;
    private final CgCalendarService calendarService;

    /**
     * @method      saveTrans
     * @author      note
     * @date        2025-04-06
     * @deacription 수입/지출 저장
     *              => 이에 대해 캘린더에도 정보 저장
    **/
    @Transactional
    public void saveTrans(CgTransSave request) {
        if( !ledgerService.ifHasLedger(request.getLedgerUid()) ) {
            throw  new CGException("존재하지 않는 가계부 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        if( !categoryService.ifHasCategory(request.getCategoryUid()) ) {
            throw  new CGException("존재하지 않는 카테고리 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        CgTrans cgTrans = new CgTrans(request);
        mapper.insert(cgTrans);

        Long transUid = cgTrans.getUid();
        CgCalendarSave saveRequest = CgCalendarSave.insertCalendar()
                .transUid(transUid)
                .startDate(request.getTransDate())
                .endDate(request.getTransDate())
                .userUid(UserUtil.getLoginUserUid())
                .build();
        calendarService.saveCalendar(saveRequest);
    }
}
