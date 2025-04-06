package hong.CashGuard.domain.ledger.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.ledger.domain
 * fileName       : CgLedgerMapper
 * author         : work
 * date           : 2025-04-03
 * description    : 가계부 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-06        note       countAllByLedgerUid 추가
 */

@Mapper
public interface CgLedgerMapper extends BaseMapper<CgLedger> {

    int countAllByLedgerUid(Map<String, Object> params);
}
