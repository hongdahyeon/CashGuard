package hong.CashGuard.domain.trans.service;

import hong.CashGuard.domain.calendar.dto.request.CgCalendarChange;
import hong.CashGuard.domain.calendar.dto.request.CgCalendarSave;
import hong.CashGuard.domain.calendar.dto.response.CgCalendarView;
import hong.CashGuard.domain.calendar.service.CgCalendarService;
import hong.CashGuard.domain.category.service.CgCategoryService;
import hong.CashGuard.domain.ledger.service.CgLedgerService;
import hong.CashGuard.domain.trans.domain.CgTrans;
import hong.CashGuard.domain.trans.domain.CgTransMapper;
import hong.CashGuard.domain.trans.dto.request.CgTransChange;
import hong.CashGuard.domain.trans.dto.request.CgTransParam;
import hong.CashGuard.domain.trans.dto.request.CgTransSave;
import hong.CashGuard.domain.trans.dto.response.CgTransList;
import hong.CashGuard.domain.trans.dto.response.CgTransView;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.trans.service
 * fileName       : CgTransService
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       수입/지출 수정, 단건 조회, 페이징/리스트 조회, 삭제 서비스 로직 추가
 */
@Service
@RequiredArgsConstructor
public class CgTransService {

    private final CgTransMapper mapper;
    private final CgLedgerService ledgerService;
    private final CgCategoryService categoryService;
    private final CgCalendarService calendarService;

    /**
     * @method      saveTrans
     * @author      note
     * @date        2025-04-06
     * @deacription 수입/지출 저장
     *              => 이에 대해 캘린더에도 정보 저장
    **/
    @Transactional
    public void saveTrans(CgTransSave request) {
        if( !ledgerService.ifHasLedger(request.getLedgerUid()) ) {
            throw  new CGException("존재하지 않는 가계부 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        if( !categoryService.ifHasCategory(request.getCategoryUid()) ) {
            throw  new CGException("존재하지 않는 카테고리 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        CgTrans cgTrans = new CgTrans(request);
        mapper.insert(cgTrans);

        Long transUid = cgTrans.getUid();
        CgCalendarSave saveRequest = CgCalendarSave.insertCalendar()
                .transUid(transUid)
                .startDate(request.getTransDate())
                .endDate(request.getTransDate())
                .userUid(UserUtil.getLoginUserUid())
                .build();
        calendarService.saveCalendar(saveRequest);
    }

    /**
     * @method      changeTrans
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 수정
    **/
    @Transactional
    public void changeTrans(Long uid, CgTransChange request) {
        if( !categoryService.ifHasCategory(request.getCategoryUid()) ) {
            throw  new CGException("존재하지 않는 카테고리 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        CgTrans myTransView = mapper.view(uid);
        mapper.update(myTransView.changeTrans(uid, request));

        CgCalendarChange changRequest = CgCalendarChange.updateBuilder()
                .transUid(uid)
                .startDate(request.getTransDate())
                .endDate(request.getTransDate())
                .build();
        calendarService.changeCalendar(changRequest);
    }

    /**
     * @method      findTransByUid
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 단건 조회 + 캘린더 정보
    **/
    @Transactional(readOnly = true)
    public CgTransView findTransByUid(Long uid) {
        CgTransView transDetail = mapper.getDetail(uid);
        CgCalendarView calendar = calendarService.findCalendarByTransUid(uid);
        transDetail.setCalendar(calendar);
        return transDetail;
    }

    /**
     * @method      findAllMyPage
     * @author      work
     * @date        2025-04-07
     * @deacription 현재 로그인한 사용자의 수입/지출 내역 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgTransList> findAllMyPage(CgTransParam param, Pageable pageable) {
        if( (param.getLedgerUid() != null) && !ledgerService.ifHasLedger(param.getLedgerUid()) ) {
            throw  new CGException("존재하지 않는 가계부 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        param.setUserUid(UserUtil.getLoginUserUid());
        List<CgTransList> list = mapper.page(pageable.generateMap(param));
        list.forEach(transDto -> {
            CgCalendarView calendar = calendarService.findCalendarByTransUid(transDto.getTransUid());
            transDto.setCalendar(calendar);
        });
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllMyList
     * @author      work
     * @date        2025-04-07
     * @deacription 현재 로그인한 사용자의 수입/지출 내역 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgTransList> findAllMyList(CgTransParam param) {
        if( (param.getLedgerUid() != null) && !ledgerService.ifHasLedger(param.getLedgerUid()) ) {
            throw  new CGException("존재하지 않는 가계부 정보입니다.", HttpStatus.BAD_REQUEST);
        }
        param.setUserUid(UserUtil.getLoginUserUid());
        List<CgTransList> list = mapper.list(param);
        list.forEach(transDto -> {
            CgCalendarView calendar = calendarService.findCalendarByTransUid(transDto.getTransUid());
            transDto.setCalendar(calendar);
        });
        return list;
    }

    /**
     * @method      deleteTrans
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 내역 단건 삭제
    **/
    @Transactional
    public void deleteTrans(Long uid) {
        Map<String, Object> map = Map.of(
            "transUid", uid,
            "userUid", UserUtil.getLoginUserUid()
        );
        int countTrans = mapper.checkTransUid(map);
        if(countTrans == 0) {
            throw new CGException("해당되는 거래 내역 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        mapper.delete(new CgTrans(uid));
    }
}
