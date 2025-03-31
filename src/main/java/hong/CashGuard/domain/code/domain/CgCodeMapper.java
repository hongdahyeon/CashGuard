package hong.CashGuard.domain.code.domain;

import hong.CashGuard.domain.code.dto.response.CgCodeList;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.code.domain
 * fileName       : CgCodeMapper
 * author         : home
 * date           : 2025-03-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Mapper
public interface CgCodeMapper extends BaseMapper<CgCode> {

    Integer isExistCode(String code);

    List<CgCodeList> getAllChildren(String upperCode);
}