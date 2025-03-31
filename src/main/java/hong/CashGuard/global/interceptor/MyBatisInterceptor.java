package hong.CashGuard.global.interceptor;

import hong.CashGuard.global.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * packageName    : hong.CashGuard.global.interceptor
 * fileName       : MyBatisInterceptor
 * author         : work
 * date           : 2025-03-27
 * description    : MyBatis 출력을 원하는 형식으로 변경하여 출력하기 위함
 *                  > MyBatis에 의해 출력되는 쿼리들을 인터셉트 하여 커스터마이징 하는 인터셉터 역할을 한다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Intercepts({
        @Signature(type= StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})
       ,@Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
})
@Slf4j
public class MyBatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        HttpServletRequest request = WebUtil.nowRequest();
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        if(request == null) return invocation.proceed();
        else return bindingSQL(request, handler, invocation);
    }

    /**
     * @method      bindingSQL
     * @author      work
     * @date        2025-03-27
     * @deacription SQL 쿼리와 파라미터를 받아와서 바인딩 작업을 실행
     *              => 만일 파라미터가 없는 경우라면 '?' 값으로 대체
    **/
    @SuppressWarnings("rawtypes")
    public Object bindingSQL(HttpServletRequest request, StatementHandler handler, Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        BoundSql boundSql = handler.getBoundSql();
        Object param = handler.getParameterHandler().getParameterObject();
        String sql = boundSql.getSql().replaceAll("\\s*(?=(\\r\\n|\\r|\\n))+", "");

        // if param null
        if(param == null) {
            setSqls(request, sql.replaceFirst("\\?", "''"));
        }

        // if param : int, long, float, double
        else if(param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
            String paramStr = formValue(param);
            setSqls(request, sql.replaceFirst("\\?", paramStr));
        }

        // if param : string
        else if(param instanceof String) {
            String paramStr = formValue(param);
            setSqls(request, sql.replaceFirst("\\?", "'" + paramStr + "'"));
        }

        // if param : map
        else if(param instanceof Map) {
            Map paramObjectMap = (Map) param;
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();

            for(ParameterMapping mapping : paramMapping) {
                String key = mapping.getProperty();

                try {
                    Object value = null;
                    if(boundSql.hasAdditionalParameter(key)) {  // 동적 SQL로 인해 __frch_item_0 같은 파라미터가 생성되어 적재됨, additionalParameter로 획득
                        value = boundSql.getAdditionalParameter(key);
                    } else {
                        value = paramObjectMap.get(key);
                    }

                    String valueStr = formValue(value);
                    sql = sql.replaceFirst("\\?", valueStr);

                } catch (Exception e) {
                    sql = sql.replaceFirst("\\?", "#{" + key + "}");
                }
            }

            setSqls(request, sql);
        }

        // if param : class
        else {
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
            Class<? extends Object> paramClass = param.getClass();

            for(ParameterMapping mapping : paramMapping) {
                String key = mapping.getProperty();

                try{
                    Object value = null;
                    if(boundSql.hasAdditionalParameter(key)) {          // 동적 SQL로 인해 __frch_item_0 같은 파라미터가 생성되어 적재됨, additionalParameter로 획득
                        value = boundSql.getAdditionalParameter(key);
                    } else {
                        Field field = ReflectionUtils.findField(paramClass, key);
                        field.setAccessible(true);
                        value = field.get(param);
                    }

                    String valueStr = formValue(value);
                    sql = sql.replaceFirst("\\?", valueStr);

                }catch (Exception e) {
                    sql = sql.replaceFirst("\\?", "#{" + key + "}");
                }
            }
            setSqls(request, sql);
        }


        try {
            Object result = null;
            result = invocation.proceed();
            setSqlResult(request, result);

            return  result;

        } catch (Throwable t) {
            setSqlError(request, t);
            throw t;
        }
    }

    private String formValue(Object o) {
        if(o == null) return "*";
        if(o instanceof String) return String.format("'%s'", o.toString());
        return o.toString();
    }

    /**
     * @method      setSqls
     * @author      work
     * @date        2025-03-27
     * @deacription 바인딩이 되어 수정된 SQL 쿼리를 로그에 기록 => 이후 출력
    **/
    private void setSqls(HttpServletRequest request, String sql) {
        StringBuilder sqls = getStringBuilder(request);
        sqls.append("\n        ").append(sql);
        request.setAttribute(LoggingInterceptor.MYBATIS_SQL_LOG, sqls);
    }

    /**
     * @method      setSqlResult
     * @author      work
     * @date        2025-03-27
     * @deacription 쿼리 실행 이후에 결과를 로그에 기록
     *              => <List> type    : 결과 개수
     *              => <Integer> type : 반영된 건수
    **/
    private void setSqlResult(HttpServletRequest request, Object result){
        StringBuilder sqls = getStringBuilder(request);
        if(result instanceof List<?>) sqls.append("\n ==> RESULT: ").append(((List<?>) result).size()).append(" 건 획득");
        else if(result instanceof Integer) sqls.append("\n ==> RESULT: ").append(((Integer) result).intValue()).append(" 건 반영");
        sqls.append("\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        request.setAttribute(LoggingInterceptor.MYBATIS_SQL_LOG, sqls);
    }

    /**
     * @method      setSqlError
     * @author      work
     * @date        2025-03-27
     * @deacription 쿼리 실행 도중에 오류 발생시, 오류 메시지를 로그에 기록
    **/
    private void setSqlError(HttpServletRequest request, Throwable t) {
        StringBuilder sqls = getStringBuilder(request);
        sqls.append("\n ==> ERROR: ").append(t.getCause());
        sqls.append("\n=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        request.setAttribute(LoggingInterceptor.MYBATIS_SQL_LOG, sqls);
    }

    public StringBuilder getStringBuilder(HttpServletRequest request){
        StringBuilder sqls = (StringBuilder) request.getAttribute(LoggingInterceptor.MYBATIS_SQL_LOG);
        if(sqls == null) {
            sqls = new StringBuilder();
        }
        return sqls;
    }

    /**
     * @method      plugin
     * @author      work
     * @date        2025-03-27
     * @deacription {target} 객체를 현재의 인터셉터로 래핑함으로써, 해당 객체가 인터셉터의 동작을 포함하는 새로운 객체로 바뀌게 되어 추가된 동작이나 로직이 수행된다.
     *              => {plugin} 메서드는 {target} 객체에 인터셉터의 동작을 적용할 수 있도록 도와주는 역할을 한다
     *                 만일 {target} 객체만 그대로 리턴하게 되면 기존 상태 그대로 실행되기에 상속받아서 인터셉터에 추가한 동작이나 로직이 적용되지 않게 된다.
    **/
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 설정이 필요한 경우 여기에 구현
    }

}
