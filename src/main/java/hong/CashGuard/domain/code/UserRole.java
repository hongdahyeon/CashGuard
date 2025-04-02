package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : UserRole
 * author         : work
 * date           : 2025-04-02
 * description    : 유저 권한 enum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter
public enum UserRole {

    ROLE_SUPER("SUPER", "슈퍼 권한"),
    ROLE_MANAGER("MANAGER", "매니저 권한"),
    ROLE_USER("USER", "유저 권한");

    private String role;
    private String roleNm;

    UserRole(String role, String roleNm) {
        this.role = role;
        this.roleNm = roleNm;
    }

    public static String getRoleNm(String role) {
        for( UserRole userRole : UserRole.values() ) {
            if(userRole.name().equals(role)) {
                return userRole.getRoleNm();
            }
        }
        return "";
    }
}
