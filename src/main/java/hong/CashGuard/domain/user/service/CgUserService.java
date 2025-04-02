package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.domain.CgUser;
import hong.CashGuard.domain.user.domain.CgUserMapper;
import hong.CashGuard.domain.user.dto.request.CgUserChange;
import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserPassword;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.domain.user.dto.response.CgUserList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.user.service
 * fileName       : CgUserService
 * author         : work
 * date           : 2025-03-27
 * description    : 기본적인 유저 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-03-31        home       page, list 서비스 로직 추가
 * 2025-04-01        work       update 서비스 로직 추가
 */

@Service
@RequiredArgsConstructor
public class CgUserService {

    private final CgUserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * @method      saveUser
     * @author      work
     * @date        2025-03-27
     * @deacription 사용자 정보 저장
    **/
    @Transactional
    public void saveUser(CgUserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        mapper.insert(new CgUser(request, encodePassword));
    }

    /**
     * @method      changeUser
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 정보 수정
    **/
    @Transactional
    public void changeUser(Long uid, CgUserChange request) {
        CgUser myInfo = mapper.view(uid);
        String encodePassword = null;
        if( request.getPassword() != null && !request.getPassword().isEmpty() ) {
            encodePassword = passwordEncoder.encode(request.getPassword());
        }
        mapper.update(myInfo.changeUser(uid, request, encodePassword));
    }

    /**
     * @method      findAllUserPage
     * @author      home
     * @date        2025-03-31
     * @deacription 사용자 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgUserList> findAllUserPage(CgUserParam param, Pageable pageable) {
        List<CgUserList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllUserList
     * @author      home
     * @date        2025-03-31
     * @deacription 사용자 정보 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgUserList> findAllUserList(CgUserParam param) {
        return mapper.list(param);
    }

    /**
     * @method      changePassword
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 비밀번호 변경
    **/
    @Transactional
    public void changePassword(CgUserPassword request) {
        String changeYn = request.getChangeYn();
        String encodePassword = null;
        if( "Y".equals(changeYn) ) {

            if( request.getPassword() == null || request.getPassword().isEmpty() ) {
                throw new CGException("비밀번호가 입력되지 않았습니다.", HttpStatus.BAD_REQUEST);
            }

            encodePassword = passwordEncoder.encode(request.getPassword());
        }
        mapper.updateLastPasswdChngDt(new CgUser(request.getUserId(), encodePassword));
    }

    /**
     * @method      unLockUser
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 잠김 풀기
    **/
    @Transactional
    public void unLockUser(Long uid) {
        mapper.updateUserToUnLock(new CgUser(uid));
    }

    /**
     * @method      enableDisableUser
     * @author      work
     * @date        2025-04-01
     * @deacription 사용자 계정 활성화/비활성화
    **/
    @Transactional
    public void enableDisableUser(Long uid, boolean enable) {
        String isEnable = (enable) ? "Y" : "N";
        mapper.updateUserIsEnable(new CgUser(uid, isEnable));
    }

}
