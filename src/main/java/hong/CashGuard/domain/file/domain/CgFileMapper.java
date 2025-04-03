package hong.CashGuard.domain.file.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.file.domain
 * fileName       : CgFileMapper
 * author         : home
 * date           : 2025-04-03
 * description    : 첨부파일 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 */
@Mapper
public interface CgFileMapper extends BaseMapper<CgFile> {

    Long generateKey();
}
