package hong.CashGuard.domain.budget.service;

import hong.CashGuard.domain.budget.domain.CgBudget;
import hong.CashGuard.domain.budget.domain.CgBudgetMapper;
import hong.CashGuard.domain.budget.dto.request.CgBudgetChange;
import hong.CashGuard.domain.budget.dto.request.CgBudgetParam;
import hong.CashGuard.domain.budget.dto.request.CgBudgetSave;
import hong.CashGuard.domain.budget.dto.response.CgBudgetList;
import hong.CashGuard.domain.budget.dto.response.CgBudgetView;
import hong.CashGuard.domain.code.BudgetPeriod;
import hong.CashGuard.domain.code.BudgetTrans;
import hong.CashGuard.domain.ledger.service.CgLedgerService;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.budget.service
 * fileName       : CgBudgetService
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CgBudgetService {

    private final CgBudgetMapper mapper;
    private final CgLedgerService ledgerService;

    /**
     * @method      saveBudget
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 저장
    **/
    @Transactional
    public void saveBudget(CgBudgetSave request){
        if( !ledgerService.ifHasLedger(request.getLedgerUid()) ) {
            throw  new CGException("존재하지 않는 가계부 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        Long loginUserUid = UserUtil.getLoginUserUid();
        mapper.insert(new CgBudget(request, loginUserUid));
    }

    /**
     * @method      changeBudget
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 수정
    **/
    @Transactional
    public void changeBudget(Long uid, CgBudgetChange request) {
        CgBudget myBudget = mapper.view(uid);
        mapper.update(myBudget.changeBudget(uid, request));
    }

    /**
     * @method      findBudgetByUid
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 상세 조회
    **/
    @Transactional(readOnly = true)
    public CgBudgetView findBudgetByUid(Long uid) {
        return mapper.getDetail(uid);
    }

    /**
     * @method      findAllPage
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgBudgetList> findAllPage(CgBudgetParam param, Pageable pageable) {
        this.paramException(param);
        param.setUserUid(UserUtil.getLoginUserUid());
        List<CgBudgetList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }


    /**
     * @method      findAllList
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgBudgetList> findAllList(CgBudgetParam param) {
        this.paramException(param);
        param.setUserUid(UserUtil.getLoginUserUid());
        return mapper.list(param);
    }

    /**
     * @method      paramException
     * @author      note
     * @date        2025-04-06
     * @deacription {param} => type & val 값은 함께 움직인다.
    **/
    private void paramException(CgBudgetParam param) {
        if( (param.getPeriodType() != null) && !BudgetPeriod.isValidPeriod(param.getPeriodType()) ) {
            throw new CGException("유효하지 않은 예산 목표 기간 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        if( (param.getTransTpCd() != null) && !BudgetTrans.isValidTrans(param.getTransTpCd()) ) {
            throw new CGException("유효하지 않은 예산 목표 코드입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @method      deleteBudget
     * @author      note
     * @date        2025-04-06
     * @deacription 예산 목표 삭제
    **/
    @Transactional
    public void deleteBudget(Long uid) {
        mapper.delete(new CgBudget(uid));
    }
}
