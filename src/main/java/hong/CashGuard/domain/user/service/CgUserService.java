package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.domain.CgUser;
import hong.CashGuard.domain.user.domain.CgUserMapper;
import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.domain.user.dto.response.CgUserList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import lombok.RequiredArgsConstructor;
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
 */

@Service
@RequiredArgsConstructor
public class CgUserService {

    private final CgUserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(CgUserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        mapper.insert(new CgUser(request, encodePassword));
    }

    @Transactional(readOnly = true)
    public Page<CgUserList> findAllUserPage(CgUserParam param, Pageable pageable) {
        List<CgUserList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }


    @Transactional(readOnly = true)
    public List<CgUserList> findAllUserList(CgUserParam param) {
        return mapper.list(param);
    }

}
