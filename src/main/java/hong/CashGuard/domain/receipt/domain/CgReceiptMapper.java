package hong.CashGuard.domain.receipt.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.receipt.domain
 * fileName       : CgReceiptMapper
 * author         : work
 * date           : 2025-04-08
 * description    : 영수증 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Mapper
public interface CgReceiptMapper extends BaseMapper<CgReceipt> {
}
