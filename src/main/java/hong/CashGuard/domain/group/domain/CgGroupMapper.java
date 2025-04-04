package hong.CashGuard.domain.group.domain;

import hong.CashGuard.domain.group.dto.response.CgGroupAndMemberAndCategoryList;
import hong.CashGuard.domain.group.dto.response.CgGroupInfo;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.group.domain
 * fileName       : CgGroupMapper
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 및 그룹 하위 사용자 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       member 관련 부분 분리
 * 2025-04-04        work       getGroupListByUserUid 추가 -> {userUid} 가 속하거나, 생성한 활성화된 그룹
 */

@Mapper
public interface CgGroupMapper extends BaseMapper<CgGroup> {

    int checkIfExist(Map<String, Object> params);

    List<CgGroupAndMemberAndCategoryList> getLoginUsersGroup(Long userUid);

    List<CgGroupInfo> getGroupListByUserUid(Long userUid);
}
