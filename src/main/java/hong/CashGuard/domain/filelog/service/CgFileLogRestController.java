package hong.CashGuard.domain.filelog.service;

import hong.CashGuard.domain.filelog.dto.request.CgFileLogSave;
import hong.CashGuard.domain.filelog.dto.response.CgFileLogList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.filelog.service
 * fileName       : CgFileLogRestController
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 로그 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/file-log")
public class CgFileLogRestController {

    private final CgFileLogService service;

    /**
     *
     * 파일 로그 저장
     *
     * @api         [POST] /cguard/api/file-log
     * @author      work
     * @date        2025-04-04
    **/
    @PostMapping
    public ResponseEntity saveFileLog(@RequestBody @Valid CgFileLogSave request) {
        service.saveFileLog(request);
        return  ResponseEntity.ok().build();
    }

    /**
     *
     * 파일 로그 목록 조회 (리스트)
     * (by. fileUid)
     *
     * @api         [GET] /cguard/api/file-log/list/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/list/{uid}")
    public ResponseEntity findAllList(@PathVariable("uid") Long uid) {
        List<CgFileLogList> allList = service.findAllList(uid);
        return ResponseEntity.ok(allList);
    }
}
