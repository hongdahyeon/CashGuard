package hong.CashGuard.domain.trans.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.trans.domain
 * fileName       : CgTransMapper
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Mapper
public interface CgTransMapper extends BaseMapper<CgTrans> {
}
