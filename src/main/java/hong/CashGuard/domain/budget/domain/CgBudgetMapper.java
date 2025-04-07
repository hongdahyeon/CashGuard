package hong.CashGuard.domain.budget.domain;

import hong.CashGuard.domain.budget.dto.response.CgBudgetList;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
 * 2025-04-07        work       * getAllBudgetList 추가 : 예산 초과 여부 체크를 위하 알람 발송 Y로 저장된 예산 목록 가져오기
 *                              * getSendAlarmByUid : {budgetUid} 값으로 send_alarm 값 가져오기
 *                              * changeAlarmByUid : {budgetUid} 값으로 send_alarm 'Y'로 수정하기
 */
@Mapper
public interface CgBudgetMapper extends BaseMapper<CgBudget> {

    List<CgBudgetList> getAllBudgetList();

    String getSendAlarmByUid(Long budgetUid);

    int changeAlarmByUid(Long budgetUid);
}
