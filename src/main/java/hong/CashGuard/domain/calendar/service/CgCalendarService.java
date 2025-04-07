package hong.CashGuard.domain.calendar.service;

import hong.CashGuard.domain.calendar.domain.CgCalendar;
import hong.CashGuard.domain.calendar.domain.CgCalendarMapper;
import hong.CashGuard.domain.calendar.dto.request.CgCalendarChange;
import hong.CashGuard.domain.calendar.dto.request.CgCalendarSave;
import hong.CashGuard.domain.calendar.dto.response.CgCalendarView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.calendar.service
 * fileName       : CgCalendarService
 * author         : note
 * date           : 2025-04-06
 * description    : 수입/지출 캘린더 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-06        note       최초 생성
 * 2025-04-07        work       * 수입/지출 캘린더 수정 로직 추가
 *                              * 수입/지출 캘린더 조회 로직 추가 by. transUid
 */
@Service
@RequiredArgsConstructor
public class CgCalendarService {

    private final CgCalendarMapper mapper;

    /**
     * @method      saveCalendar
     * @author      note
     * @date        2025-04-06
     * @deacription 수입/지출 캘린더 저장
    **/
    @Transactional
    public void saveCalendar(CgCalendarSave request) {
        mapper.insert(new CgCalendar(request));
    }

    /**
     * @method      changeCalendar
     * @author      work
     * @date        2025-04-07
     * @deacription 수입/지출 캘린더 수정
    **/
    @Transactional
    public void changeCalendar(CgCalendarChange request) {
        mapper.update(new CgCalendar(request));
    }

    /**
     * @method      findCalendarByTransUid
     * @author      work
     * @date        2025-04-07
     * @deacription {transUid} 값으로 캘린더 정보 조회
    **/
    @Transactional(readOnly = true)
    public CgCalendarView findCalendarByTransUid(Long transUid) {
        return mapper.getDetail(transUid);
    }
}
