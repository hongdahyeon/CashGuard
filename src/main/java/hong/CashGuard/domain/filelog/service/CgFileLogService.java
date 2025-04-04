package hong.CashGuard.domain.filelog.service;

import hong.CashGuard.domain.filelog.domain.CgFileLog;
import hong.CashGuard.domain.filelog.domain.CgFileLogMapper;
import hong.CashGuard.domain.filelog.dto.request.CgFileLogSave;
import hong.CashGuard.domain.filelog.dto.response.CgFileLogList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.filelog.service
 * fileName       : CgFileLogService
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 로그 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgFileLogService {

    private final CgFileLogMapper mapper;

    /**
     * @method      saveFileLog
     * @author      work
     * @date        2025-04-04
     * @deacription 파일 로그 저장
    **/
    @Transactional
    public void saveFileLog(CgFileLogSave request){
        mapper.insert(new CgFileLog(request));
    }

    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-04
     * @deacription 파일 로그 목록 조회 (by. fileUid)
    **/
    @Transactional(readOnly = true)
    public List<CgFileLogList> findAllList(Long fileUid) {
        return mapper.list(fileUid);
    }
}
