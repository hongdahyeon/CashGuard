package hong.CashGuard.domain.group.domain.member;

import hong.CashGuard.domain.group.dto.response.member.CgGroupMemberList;
import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.group.domain.member
 * fileName       : CgGroupMemberMapper
 * author         : work
 * date           : 2025-04-03
 * description    : 그룹 하위 멤버 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Mapper
public interface CgGroupMemberMapper extends BaseMapper<CgGroupMember> {

    int approveMember(CgGroupMember bean);

    List<CgGroupMemberList> getAllGroupMember(Long groupUid);
}
