package hong.CashGuard.domain.comment.service;

import hong.CashGuard.domain.comment.domain.CgBoardComment;
import hong.CashGuard.domain.comment.domain.CgBoardCommentMapper;
import hong.CashGuard.domain.comment.dto.request.CgBoardCommentChange;
import hong.CashGuard.domain.comment.dto.request.CgBoardCommentSave;
import hong.CashGuard.domain.comment.dto.response.CgBoardCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.comment.service
 * fileName       : CgBoardCommentService
 * author         : work
 * date           : 2025-04-04
 * description    : 댓글 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgBoardCommentService {

    private final CgBoardCommentMapper mapper;

    /**
     * @method      saveComment
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 저장
    **/
    @Transactional
    public void saveComment(Long boardUid, CgBoardCommentSave request, Long upperUid) {
        mapper.insert(new CgBoardComment(boardUid, request, upperUid));
    }

    /**
     * @method      changeComment
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 수정
    **/
    @Transactional
    public void changeComment(Long commentUid, CgBoardCommentChange request) {
        mapper.update(new CgBoardComment(commentUid, request));
    }

    /**
     * @method      deleteComment
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 삭제
    **/
    @Transactional
    public void deleteComment(Long commentUid) {
        mapper.delete(new CgBoardComment(commentUid));
    }

    /**
     * @method      findAllCommentTree
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 트리 구조 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CgBoardCommentList> findAllCommentTree(Long boardUid) {
        List<CgBoardCommentList> list = mapper.list(boardUid);
        return this.commentTree(list);
    }

    /**
     * @method      commentTree
     * @author      work
     * @date        2025-04-04
     * @deacription 댓글 목록을 트리 구조로 변환하여 리턴
    **/
    private List<CgBoardCommentList> commentTree(List<CgBoardCommentList> flatList) {
        Map<Long, CgBoardCommentList> map = new HashMap<>();
        List<CgBoardCommentList> rootList = new ArrayList<>();  // {upperUid == null} 댓글 리스트
        for (CgBoardCommentList dto : flatList) {
            map.put(dto.getUid(), dto);
        }
        for (CgBoardCommentList dto : flatList) {
            if (dto.getUpperUid() == null) {
                rootList.add(dto); // 최상위 댓글
            } else {
                CgBoardCommentList parent = map.get(dto.getUpperUid());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return rootList;
    }
}
