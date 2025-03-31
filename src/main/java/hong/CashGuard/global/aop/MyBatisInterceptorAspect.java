package hong.CashGuard.global.aop;

import hong.CashGuard.global.auth.PrincipalDetails;
import hong.CashGuard.global.bean.AuditBean;
import hong.CashGuard.global.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * packageName    : hong.CashGuard.global.aop
 * fileName       : MyBatisInterceptorAspect
 * author         : work
 * date           : 2025-03-28
 * description    : AuditBean 값에 대해 AOP로 분리하여 세팅해주기
 *                  > insert , update 단어로 '** 시작하는 **' 값에 대해서만 동작
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-28        work       최초 생성
 */

@Slf4j
@Aspect         // AOP 를 사용하겠다.
@Component      // @Aspect 어노테이션과 함께 같이 사용해준다 (빈등록)
public class MyBatisInterceptorAspect {

    @Around(
            "execution(* hong.CashGuard..domain.mapper.*.insert*(..)) || " +
                    "execution(* hong.CashGuard..domain.mapper.*.update*(..)) || " +
                    "execution(* hong.CashGuard.global.bean.BaseMapper.insert*(..)) || " +
                    "execution(* hong.CashGuard.global.bean.BaseMapper.update*(..))"
    )
    public Object beforeInsertOrUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userUid = (UserUtil.isAuthenticated(authentication)) ?
                ((PrincipalDetails) authentication.getPrincipal()).getUser().getUid() : 0L;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof AuditBean) {
                AuditBean auditBean = (AuditBean) arg;
                auditBean.setRegUid(userUid);
                auditBean.setUpdtUid(userUid);
            }
        }

        return joinPoint.proceed();
    }
}
