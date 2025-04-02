package hong.CashGuard.domain.group.domain;

import hong.CashGuard.domain.group.domain.member.CgGroupMember;
import hong.CashGuard.domain.group.dto.response.CgGroupAndMemberList;
import hong.CashGuard.domain.group.dto.response.CgGroupMemberList;
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
 */

@Mapper
public interface CgGroupMapper extends BaseMapper<CgGroup> {

    List<CgGroupMemberList> getAllGroupMember(Long groupUid);

    int insertMember(CgGroupMember bean);

    int approveMember(CgGroupMember bean);

    int checkIfExist(Map<String, Object> params);

    List<CgGroupAndMemberList> getLoginUsersGroup(Long userUid);
}
