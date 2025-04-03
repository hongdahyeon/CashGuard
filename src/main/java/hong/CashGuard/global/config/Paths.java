package hong.CashGuard.global.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * packageName    : hong.CashGuard.global.config
 * fileName       : Paths
 * author         : work
 * date           : 2025-03-27
 * description    : SecurityConfig 영역에서 이용되는 paths 값들
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-01        work       PUBLIC_API 추가 ( 해당 부분은 Method & API 값이 동일해야 접근 가능 )
 * 2025-04-03        work       로그인 이전에 접근 가능한 API 추가 : /api/invite-link/**
 */
public class Paths {

    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final AntPathRequestMatcher[] BEFORE_LOGIN = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/login")
            ,new AntPathRequestMatcher("/api/invite-link/**")
            ,new AntPathRequestMatcher("/csrf")
            ,new AntPathRequestMatcher("/assets/**")
            ,new AntPathRequestMatcher("/login/force.json")
    };

    public static final AntPathRequestMatcher[] PUBLIC_API = new AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/cguard/api/user", HttpMethod.POST.name())
           ,new AntPathRequestMatcher("/cguard/api/user/change-password", HttpMethod.PUT.name())

    };

    public static final AntPathRequestMatcher[] AFTER_LOGIN = new  AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/cguard/api/**")
           ,new AntPathRequestMatcher("/cguard/**")
    };

    public static final AntPathRequestMatcher[] ROLE_SUPER = new  AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/cguard/api/**/super/**")
           ,new AntPathRequestMatcher("/cguard/super/**")
    };

    public static final AntPathRequestMatcher[] ROLE_MANAGER = new  AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/cguard/api/**/manager/**")
           ,new AntPathRequestMatcher("/cguard/manager/**")
    };
}
