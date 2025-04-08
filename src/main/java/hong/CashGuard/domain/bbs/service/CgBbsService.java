package hong.CashGuard.domain.bbs.service;

import hong.CashGuard.domain.bbs.domain.CgBbs;
import hong.CashGuard.domain.bbs.domain.CgBbsMapper;
import hong.CashGuard.domain.bbs.dto.request.CgBbsChange;
import hong.CashGuard.domain.bbs.dto.request.CgBbsParam;
import hong.CashGuard.domain.bbs.dto.request.CgBbsSave;
import hong.CashGuard.domain.bbs.dto.response.CgBbsList;
import hong.CashGuard.domain.bbs.dto.response.CgBbsView;
import hong.CashGuard.domain.board.service.CgBoardService;
import hong.CashGuard.domain.code.BbsTpCd;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.bbs.service
 * fileName       : CgBbsService
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 관련 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 * 2025-04-08        work       게시판 삭제 이전에, 하위 게시글 존재 여부 체크
 */

@Service
@RequiredArgsConstructor
public class CgBbsService {

    private final CgBbsMapper mapper;
    private final CgBoardService boardService;

    /**
     * @method      saveBbs
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 저장
    **/
    @Transactional
    public void saveBbs(CgBbsSave request) {
        if( !BbsTpCd.isValidBbsTpCd(request.getBbsTpCd()) ) {
            throw new CGException("유효하지 않은 게시판 유형 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        mapper.insert(new CgBbs(request));
    }

    /**
     * @method      changeBbs
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 수정
    **/
    @Transactional
    public void changeBbs(Long uid, CgBbsChange request) {
        if( !BbsTpCd.isValidBbsTpCd(request.getBbsTpCd()) ) {
            throw new CGException("유효하지 않은 게시판 유형 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        CgBbs myView = mapper.view(uid);
        mapper.update(myView.changeBbs(uid, request));
    }

    /**
     * @method      findBbsByUid
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 단건 조회
    **/
    @Transactional(readOnly = true)
    public CgBbsView findBbsByUid(Long uid) {
        return mapper.getDetail(uid);
    }

    /**
     * @method      findAllPage
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgBbsList> findAllPage(CgBbsParam param, Pageable pageable) {
        if(param.getBbsTpCd() != null) {
            if( !BbsTpCd.isValidBbsTpCd(param.getBbsTpCd()) ) {
                throw new CGException("유효하지 않은 게시판 유형 코드입니다.", HttpStatus.BAD_REQUEST);
            }
        }
        List<CgBbsList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgBbsList> findAllList(CgBbsParam param) {
        if(param.getBbsTpCd() != null) {
            if( !BbsTpCd.isValidBbsTpCd(param.getBbsTpCd()) ) {
                throw new CGException("유효하지 않은 게시판 유형 코드입니다.", HttpStatus.BAD_REQUEST);
            }
        }
        return mapper.list(param);
    }

    /**
     * @method      deleteBbs
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 단건 삭제
    **/
    @Transactional
    public void deleteBbs(Long bbsUid) {
        int countAllByBbsUid = boardService.countAllByBbsUid(bbsUid);
        if( countAllByBbsUid != 0 ) {
            throw new CGException(bbsUid + "번 게시판 하위에 게시글이 존재하여 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        mapper.delete(new CgBbs(bbsUid));
    }
}
