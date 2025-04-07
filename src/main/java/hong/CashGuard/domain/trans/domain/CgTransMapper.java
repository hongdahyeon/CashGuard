package hong.CashGuard.domain.trans.domain;

import hong.CashGuard.domain.trans.dto.request.CgTransCheck;
import hong.CashGuard.domain.trans.dto.response.CgTransCheckAlert;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

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
 * 2025-04-07        work       * checkTransUid 추가 : {trnasUid, userUid} 묶음으로 해당 거래정보가 유저것인지 체크
 *                              * 예산 목표 초과 체크 스케줄러 : checkTransExceed, getAlertUserInfo
 */
@Mapper
public interface CgTransMapper extends BaseMapper<CgTrans> {

    int checkTransUid(Map<String, Object> map);

    long checkTransExceed(CgTransCheck request);

    CgTransCheckAlert getAlertUserInfo(Long ledgerUid);
}
