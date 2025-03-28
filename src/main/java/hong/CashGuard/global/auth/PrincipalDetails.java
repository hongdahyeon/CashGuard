package hong.CashGuard.global.auth;

import hong.CashGuard.global.auth.dto.CgSessionUser;
import hong.CashGuard.global.util.TimeUtil;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.global.auth
 * fileName       : PrincipalDetails
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@EqualsAndHashCode(of = {"user"})
public class PrincipalDetails implements UserDetails {

    private final CgSessionUser user;
    private Map<String, Object> attributes;

    public PrincipalDetails(CgSessionUser user) {
        this.user = user;
    }

    public CgSessionUser getUser() {
        return user;
    }

    public PrincipalDetails(CgSessionUser user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(user.getRole()));
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserNm();
    }

    /* 계정 만료 여부 : 마지막 로그인 날짜가 1년이 지났는지 체크 */
    @Override
    public boolean isAccountNonExpired() {
        return TimeUtil.isXYearAfter(user.getLastConnDt(), 1);
    }

    /* 계정 잠김 여부 : "Y"가 아니면 잠금 해제된 상태 (true) */
    @Override
    public boolean isAccountNonLocked() {
        return !"Y".equals(user.getIsLocked());
    }

    /* 비밀번호 만료 여부 : 만료일이 오늘을 지났는지 ( 비밀번호 변경일은 변경일로부터 90일 ) */
    @Override
    public boolean isCredentialsNonExpired() {
        return TimeUtil.dateCompare(user.getLastPasswdChngDt());
    }

    /* 계정 활성화 여부 : "Y"이면 활성화된 상태 (true) */
    @Override
    public boolean isEnabled() {
        return "Y".equals(user.getIsEnable());
    }

}

