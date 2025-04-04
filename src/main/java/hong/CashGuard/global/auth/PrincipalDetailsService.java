package hong.CashGuard.global.auth;

import hong.CashGuard.domain.group.dto.response.CgGroupInfo;
import hong.CashGuard.domain.group.service.CgGroupService;
import hong.CashGuard.domain.user.dto.response.CgUserView;
import hong.CashGuard.domain.user.service.CgSecurityUserService;
import hong.CashGuard.global.auth.dto.CgSessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : hong.CashGuard.global.auth
 * fileName       : PrincipalDetailsService
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-01        work       cgSessionUser 생성영역 => if문 안으로 넣기
 * 2025-04-04        work       customUser > 그룹 정보 추가  (1명의 유저는 여러 그룹에 속하면서도 다른 그룹에 대표일 수도 있다)
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final CgSecurityUserService userService;
    private final CgGroupService groupService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        CgUserView user = userService.getUserByUserId(userId);
        if(user != null) {
            CgSessionUser cgSessionUser = new CgSessionUser(user); // cg-user to session-user
            this.customUser(cgSessionUser);
            return new PrincipalDetails(cgSessionUser);
        } else throw new UsernameNotFoundException(userId + " 사용자가 없습니다.");
    }

    public void customUser(CgSessionUser user) {
        // user 세션에 담을 것들 담기 ex. menu
        // user.setMenu(menu)
        List<CgGroupInfo> groups= groupService.findGroupListByUserUid(user.getUid());
        user.setGroups(groups);
    }
}
