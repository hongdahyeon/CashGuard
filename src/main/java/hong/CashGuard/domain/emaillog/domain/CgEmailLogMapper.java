package hong.CashGuard.domain.emaillog.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.emaillog.domain
 * fileName       : CgEmailLogMapper
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 로그 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-07        work       changeIsRead 추가 : {uid} 값으로 is_read 업데이트
 */

@Mapper
public interface CgEmailLogMapper extends BaseMapper<CgEmailLog> {

    boolean isInvalidToken(String token);

    int updateTokenIsRead(String token);

    int changeIsRead(Long uid);
}
