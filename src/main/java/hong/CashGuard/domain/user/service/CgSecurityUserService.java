package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.domain.CgUserMapper;
import hong.CashGuard.domain.user.dto.response.CgUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.user.service
 * fileName       : CgSecurityUserService
 * author         : work
 * date           : 2025-03-27
 * description    : 시큐리티 관련 유저 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgSecurityUserService {

    private final CgUserMapper mapper;

    /**
     * @method      getUserByUserId
     * @author      work
     * @date        2025-03-27
     * @deacription {userId} 값으로 유저 기본 정보들 가져오기
     **/
    @Transactional(readOnly = true)
    public CgUserView getUserByUserId(String userId) {
        return mapper.findUserByUserId(userId);
    }

    /**
     * @method      resetLastLoginDtAndPwdFailCnt
     * @author      work
     * @date        2025-03-27
     * @deacription 로그인 성공 시점에 마지막 로그인 일자, 비번 실패 횟수 초기화
     **/
    @Transactional
    public void resetLastLoginDtAndPwdFailCnt(String userId) {
        mapper.updateLastLoginDtAndPwdFailCnt(userId);
    }

    /**
     * @method      changePwdFailCnt
     * @author      work
     * @date        2025-03-27
     * @deacription 로그인 시점, 비밀번호 오류 : 비밀번호 실패 횟수 업데이트
     *              => 만약 5번이 된 시점이라면 사용자 락 걸기
     **/
    @Transactional
    public void changePwdFailCnt(String userId, Integer pwdFailCnt) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("userId", userId);
            put("pwdFailCnt", pwdFailCnt);
        }};
        mapper.updatePwdFailCnt(params);
    }
}
