package hong.CashGuard.global.core;

/**
 * packageName    : hong.CashGuard.global.core
 * fileName       : GlobalMapper
 * author         : work
 * date           : 2025-03-27
 * description    : 기본 CRUD에 대한 매퍼 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */
public interface BaseMapper<T> {

    int insert(T bean);

    int update(T bean);

    int delete(Long uid);

    T view(Long uid);
}