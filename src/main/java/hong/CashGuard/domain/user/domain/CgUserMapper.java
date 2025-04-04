package hong.CashGuard.domain.user.domain;

import hong.CashGuard.domain.user.dto.response.CgUserView;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.user.domain
 * fileName       : CgUserMapper
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-01        work       * 리턴값 {update}의 경우 int로 변경
 *                              * updateLastPasswdChngDt, updateUserToUnLock 추가
 * 2025-04-04        work       countByUserId, countByUserEmail 추가
 */

@Mapper
public interface CgUserMapper extends BaseMapper<CgUser> {

    CgUserView findUserByUserId(String userId);

    int updateLastLoginDtAndPwdFailCnt(String userId);

    int updatePwdFailCnt(Map<String, Object> params);

    int updateLastPasswdChngDt(CgUser bean);

    int updateUserToUnLock(CgUser bean);

    int updateUserIsEnable(CgUser bean);

    boolean checkIfAuthUser(String email);

    CgUserView findUserByUserEmail(String email);

    int countByUserId(String userId);
    int countByUserEmail(String userEmail);
}
