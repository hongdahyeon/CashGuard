package hong.CashGuard.global.config;

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
 */
public class Paths {

    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final AntPathRequestMatcher[] BEFORE_LOGIN = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/login")
            ,new AntPathRequestMatcher("/csrf")
            ,new AntPathRequestMatcher("/cguard/api/user/**")
            ,new AntPathRequestMatcher("/login/force.json")
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
