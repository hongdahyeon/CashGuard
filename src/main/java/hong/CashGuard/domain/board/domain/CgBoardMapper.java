package hong.CashGuard.domain.board.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.board.domain
 * fileName       : CgBoardMapper
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 * 2025-04-08        work       {bbsUid} 하위에 게시글 개수 카운팅
 */

@Mapper
public interface CgBoardMapper extends BaseMapper<CgBoard> {

    int countAllByBbsUid(Long bbsUid);
}
