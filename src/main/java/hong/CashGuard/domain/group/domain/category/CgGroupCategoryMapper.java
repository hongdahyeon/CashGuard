package hong.CashGuard.domain.group.domain.category;

import hong.CashGuard.domain.group.dto.response.category.CgGroupCategoryList;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.group.domain.category
 * fileName       : CgGroupCategoryMapper
 * author         : work
 * date           : 2025-04-03
 * description    : 그룹 하위 카테고리 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Mapper
public interface CgGroupCategoryMapper extends BaseMapper<CgGroupCategory> {

    List<CgGroupCategoryList> getAllGroupCategory(Long groupUid);
}
