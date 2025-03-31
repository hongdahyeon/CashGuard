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
 */

@Mapper
public interface CgUserMapper extends BaseMapper<CgUser> {

    CgUserView findUserByUserId(String userId);

    void updateLastLoginDtAndPwdFailCnt(String userId);

    void updatePwdFailCnt(Map<String, Object> params);
}
