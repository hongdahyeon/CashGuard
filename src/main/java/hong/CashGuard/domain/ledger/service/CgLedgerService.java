package hong.CashGuard.domain.ledger.service;

import hong.CashGuard.domain.ledger.domain.CgLedger;
import hong.CashGuard.domain.ledger.domain.CgLedgerMapper;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerChange;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerParam;
import hong.CashGuard.domain.ledger.dto.request.CgLedgerSave;
import hong.CashGuard.domain.ledger.dto.response.CgLedgerList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.ledger.service
 * fileName       : CgLedgerService
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 관련  서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgLedgerService {

    private final CgLedgerMapper mapper;

    /**
     * @method      saveLedger
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 생성
    **/
    @Transactional
    public void saveLedger(CgLedgerSave request) {
        Long loginUserUid = UserUtil.getLoginUser().getUid();
        mapper.insert(new CgLedger(loginUserUid, request));
    }

    /**
     * @method      changeLedger
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 정보 수정
    **/
    @Transactional
    public void changeLedger(Long uid, CgLedgerChange request) {
        CgLedger ledgerView = mapper.view(uid);
        mapper.update(ledgerView.changeLedger(uid, request));
    }

    /**
     * @method      deleteLedger
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 정보 삭제
    **/
    @Transactional
    public void deleteLedger(Long uid) {
        mapper.delete(new CgLedger(uid));
    }

    /**
     * @method      findAllLedgerUserPage
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 목록 조회 (페이징) > 로그인한 사용자 본인 것만 조회 가능
    **/
    @Transactional(readOnly = true)
    public Page<CgLedgerList> findAllLedgerUserPage(CgLedgerParam param, Pageable pageable) {
        param.setUserUid(UserUtil.getLoginUserUid());
        List<CgLedgerList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllLedgerUserList
     * @author      work
     * @date        2025-04-03
     * @deacription 가계부 목록 조회 (리스트) > 로그인한 사용자 본인 것만 조회 가능
    **/
    @Transactional(readOnly = true)
    public List<CgLedgerList> findAllLedgerUserList(CgLedgerParam param) {
        return mapper.list(param);
    }
}
