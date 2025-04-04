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
 *                              * list, count, page 추가
 * 2025-04-02        work       key 값을 파라미터로 받는 view 추가
 * 2025-04-03        work       delete(T bean) 추가
 * 2025-04-04        work       <R> List<R> list(Long uid) 추가
 * 2025-04-04        work       <R> R getDetail(Long uid) 추가
 */
public interface BaseMapper<T> {

    <B, R> List<R> page(B param);

    <B, R> List<R> list(B param);

    <R> List<R> list(Long uid);

    <B> int count(B param);

    int insert(T bean);

    int update(T bean);

    int delete(Long uid);

    int delete(T bean);

    T view(Long uid);

    T view(String key);

    <R> R getDetail(Long uid);
}