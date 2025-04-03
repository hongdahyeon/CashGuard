package hong.CashGuard.domain.ledger.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
 */

@Mapper
public interface CgLedgerMapper extends BaseMapper<CgLedger> {
}
