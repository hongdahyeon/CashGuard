package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.code.UserRole;
import hong.CashGuard.domain.user.domain.CgUser;
import hong.CashGuard.domain.user.domain.CgUserMapper;
import hong.CashGuard.domain.user.dto.request.CgUserChange;
import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserPassword;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.domain.user.dto.response.CgUserList;
import hong.CashGuard.domain.user.dto.response.CgUserView;
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
 * 2025-04-04        work       isExistUserId, isExistUserEmail 추가
 *                              => 아이디, 이메일 중복 체크
 */

@Service
@RequiredArgsConstructor
public class CgUserService {

    private final CgUserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    /**
     * @method      isExistUserId
     * @author      work
     * @date        2025-04-04
     * @deacription {userId} 값을 갖고 있는 유저가 있는지 체크
     *              -> 있다면 : true  (중복되는 userId)
     *              -> 없다면 : false (사용 가능한 userId)
    **/
    @Transactional(readOnly = true)
    public boolean isExistUserId(String userId) {
        int findUserIdCnt = mapper.countByUserId(userId);
        return findUserIdCnt == 1;
    }

    /**
     * @method      isExistUserEmail
     * @author      work
     * @date        2025-04-04
     * @deacription {userEmail} 값을 갖고 있는 유저가 있는지 체크
     *              -> 있다면 : true (중복되는 userEmail)
     *              -> 없다면 : false (사용 가능한 userEmail)
    **/
    @Transactional(readOnly = true)
    public boolean isExistUserEmail(String userEmail) {
        int findUserEmailCnt = mapper.countByUserEmail(userEmail);
        return findUserEmailCnt == 1;
    }

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

    /**
     * @method      ifAuthUser
     * @author      work
     * @date        2025-04-03
     * @deacription {email} 값을 통해 이미 회원가입된 유저인지 체크
    **/
    @Transactional(readOnly = true)
    public boolean ifAuthUser(String email) {
        return mapper.checkIfAuthUser(email);
    }

    /**
     * @method      insertTempUser
     * @author      work
     * @date        2025-04-03
     * @deacription 그룹 초대를 위한 임시 사용자 추가
    **/
    @Transactional
    public void insertTempUser(String name, String email, String password) {
        String encodePassword = passwordEncoder.encode(password);
        mapper.insert(new CgUser(name, email, encodePassword, UserRole.ROLE_USER.name()));
    }

    /**
     * @method      findUserByEmail
     * @author      work
     * @date        2025-04-03
     * @deacription {email} 값을 통해 유저 정보 조회
    **/
    @Transactional(readOnly = true)
    public CgUserView findUserByEmail(String email) {
        return mapper.findUserByUserEmail(email);
    }

}
