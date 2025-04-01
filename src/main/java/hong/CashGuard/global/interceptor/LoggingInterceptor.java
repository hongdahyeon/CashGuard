package hong.CashGuard.global.interceptor;

import hong.CashGuard.global.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jodd.util.ArraysUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

/**
 * packageName    : hong.CashGuard.global.interceptor
 * fileName       : LoggingInterceptor
 * author         : work
 * date           : 2025-03-27
 * description    : 로깅 커스터마이징
 *                  > 출력될 로그를 인터셉트 해서 원하는 형식으로 바꾸어 출력하기 위함
 *                  > {MyBatisInterceptor}과 함께 사용되어 출력될 쿼리도 커스터마이징하고 소요 시간 및 파라미터와 같은 다양한 정보도 출력해준다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static Logger sqlLog = LoggerFactory.getLogger("MYBATIS_SQL_LOG");
    public static final String MYBATIS_SQL_LOG = "mybatis_sql_log";
    public static final String MYBATIS_SQL_STOP_WATCH = "mybatis_sql_stop";

    /**
     * @method      preHandle
     * @author      work
     * @date        2025-03-27
     * @deacription Controller 처리 요청 이전 실행
    **/
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        // SQL 실행된 소요 시간을 추적할 수 있도록 해준다
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        req.setAttribute(MYBATIS_SQL_STOP_WATCH, stopWatch);
        return true;
    }

    /**
     * @method      postHandle
     * @author      work
     * @date        2025-03-27
     * @deacription Controller 처리 이후, 뷰 렌더링 이전 호출
    **/
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
        /* req.getStatus() -> 응답 status : 404, 500, 200 ... */
    }

    /**
     * @method      afterCompletion
     * @author      work
     * @date        2025-03-27
     * @deacription 요청 처리 & 뷰 렌더링 이후에 호출
     *              => 최종적으로 랜더링된 SQL 쿼리문을 출력한다.
    **/
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {

        if(ex != null) {
            log.info("ex.message: {} ", ex.getMessage());
            return;
        }

        StringBuilder logs = new StringBuilder();
        logs.append("\n===========================================================");


        String requestURI = req.getRequestURI();
        String method = req.getMethod();

        /* URI */
        logs.append("\n# URI     : ").append(requestURI);

        /* METHOD */
        logs.append("\n# METHOD  : ").append(method);

        /* PARAMS */
        int parameterLength = 0;
        StringBuilder paramText = new StringBuilder();
        Enumeration<String> _paramNames = req.getParameterNames();
        while(_paramNames.hasMoreElements()) {
            String key = _paramNames.nextElement().toString();

            String[] values = req.getParameterValues(key);
            if(values.length == 1) {
                paramText.append("\t{").append(key).append(" = ").append(StringUtil.fixLength(values[0], 50)).append("}\n");
            }
            else {
                paramText.append("\t{").append(key).append("[] = [");
                paramText.append(ArraysUtil.toString(values));
                paramText.append("]}\n");
            }
            parameterLength++;
        }
        if(parameterLength > 0) {
            logs.append("\nPARAMS     : ").append(parameterLength).append(" 건\n");
            logs.append(paramText.substring(0, paramText.length()-1));
        }

        /* SQL */
        StringBuilder sql = (StringBuilder) req.getAttribute(MYBATIS_SQL_LOG);
        if(sql != null) logs.append("\n# SQL: ").append(sql);

        StopWatch stopWatch = (StopWatch) req.getAttribute(MYBATIS_SQL_STOP_WATCH);
        if(stopWatch != null) {
            stopWatch.stop();
            double totalTime = stopWatch.getTotalTimeSeconds();
            logs.append(String.format("\n==> TIMES : %.2f sec", totalTime));
        }

        logs.append("\n===========================================================");

        sqlLog.info("{}", logs);
        req.removeAttribute(MYBATIS_SQL_LOG);
        req.removeAttribute(MYBATIS_SQL_STOP_WATCH);
    }

}
