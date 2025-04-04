package hong.CashGuard.domain.category.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.category.domain
 * fileName       : CgCategoryMapper
 * author         : work
 * date           : 2025-04-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       isExistCategoryCode => countByCategoryCd 이름 변경
 */
@Mapper
public interface CgCategoryMapper extends BaseMapper<CgCategory> {

    Integer countByCategoryCd(String code);
}
