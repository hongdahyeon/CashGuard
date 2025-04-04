package hong.CashGuard.domain.board.service;

import hong.CashGuard.domain.board.domain.CgBoard;
import hong.CashGuard.domain.board.domain.CgBoardMapper;
import hong.CashGuard.domain.board.dto.request.CgBoardChange;
import hong.CashGuard.domain.board.dto.request.CgBoardParam;
import hong.CashGuard.domain.board.dto.request.CgBoardSave;
import hong.CashGuard.domain.board.dto.response.CgBoardList;
import hong.CashGuard.domain.board.dto.response.CgBoardView;
import hong.CashGuard.domain.file.dto.response.CgFileList;
import hong.CashGuard.domain.file.service.CgFileService;
import hong.CashGuard.domain.group.service.CgGroupService;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.board.service
 * fileName       : CgBoardService
 * author         : work
 * date           : 2025-04-04
 * description    : 게시글 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgBoardService {

    private final CgBoardMapper mapper;
    private final CgFileService fileService;
    private final CgGroupService groupService;

    /**
     * @method      saveBoard
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 저장
    **/
    @Transactional
    public void saveBoard(Long groupUid, Long bbsUid, CgBoardSave request) {
        // todo  check
        Long loginUserUid = UserUtil.getLoginUserUid();
        // 아직 로그인 유저가 {groupUid} 에 속한 유저가 인라면 -> 게시글 작성 불가능
        if( groupService.canJoin(groupUid, loginUserUid) ) {
            throw new CGException("그룹 하위 게시글 작성에 대한 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Long fileUid = fileService.saveAndDelFile(null, request.getAddFile(), request.getDelFile());
        Long thumbnail = fileService.saveAndDelThumbnail(null, request.getAddThumbnail(), request.getDelThumbnail());

        mapper.insert(new CgBoard(bbsUid, groupUid, request, fileUid, thumbnail));
    }

    /**
     * @method      changeBoard
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 수정
    **/
    @Transactional
    public void changeBoard(Long boardUid, CgBoardChange request) {
        Long fileUid = fileService.saveAndDelFile(request.getFileUid(), request.getAddFile(), request.getDelFile());
        Long thumbnail = fileService.saveAndDelThumbnail(request.getThumbUid(), request.getAddThumbnail(), request.getDelThumbnail());
        mapper.update(new CgBoard(boardUid, request, fileUid, thumbnail));
    }

    /**
     * @method      deleteBoard
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 삭제
    **/
    @Transactional
    public void deleteBoard(Long boardUid) {
        mapper.delete(new CgBoard(boardUid));
    }

    /**
     * @method      findAllPageByBbsAndGroup
     * @author      work
     * @date        2025-04-04
     * @deacription {groupUid, bbsUid} 하위 게시글 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgBoardList> findAllPageByBbsAndGroup(Long groupUid, Long bbsUid, CgBoardParam param, Pageable pageable) {
        param.setInfo(groupUid, bbsUid);
        List<CgBoardList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllListByBbsAndGroup
     * @author      work
     * @date        2025-04-04
     * @deacription {groupUid, bbsUid} 하위 게시글 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgBoardList> findAllListByBbsAndGroup(Long groupUid, Long bbsUid, CgBoardParam param) {
        param.setInfo(groupUid, bbsUid);
        return mapper.list(param);
    }

    /**
     * @method      findBoardByUid
     * @author      work
     * @date        2025-04-04
     * @deacription 게시글 단건 조회 (with. file, thumbnail)
    **/
    @Transactional(readOnly = true)
    public CgBoardView findBoardByUid(Long boardUid) {
        CgBoardView detail = mapper.getDetail(boardUid);
        if(detail.getFileUid() != null) {
            List<CgFileList> files = fileService.findAllList(detail.getFileUid());
            detail.setFiles(files);
        }
        if(detail.getThumbUid() != null) {
            CgFileList thumbnail = fileService.findThumbnail(detail.getThumbUid());
            detail.setThumbnail(thumbnail);
        }
        return detail;
    }

}