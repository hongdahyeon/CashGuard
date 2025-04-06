package hong.CashGuard.domain.budget.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.budget.domain
 * fileName       : CgBudgetMapper
 * author         : note
 * date           : 2025-04-06
 * description    : 예산 목표 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 */
@Mapper
public interface CgBudgetMapper extends BaseMapper<CgBudget> {
}
