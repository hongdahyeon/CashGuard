package hong.CashGuard.global.bean;

import java.util.List;

/**
 * packageName    : hong.CashGuard.global.bean
 * fileName       : GlobalMapper
 * author         : work
 * date           : 2025-03-27
 * description    : 기본 CRUD에 대한 매퍼 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-03-31        home       * 상위 패키지명 변경 (core->bean)
 *                              * list, count 추가
 */
public interface BaseMapper<T> {

    <B, R> List<R> list(B param);

    <B> int count(B param);

    int insert(T bean);

    int update(T bean);

    int delete(Long uid);

    T view(Long uid);
}