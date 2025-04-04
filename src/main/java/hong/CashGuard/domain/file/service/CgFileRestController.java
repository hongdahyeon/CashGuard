package hong.CashGuard.domain.file.service;

import hong.CashGuard.domain.file.dto.response.CgFileList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.file.service
 * fileName       : CgFileRestController
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/file")
public class CgFileRestController {

    private final CgFileService service;

    /**
     *
     * 파일 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/file/list/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/list/{uid}")
    public ResponseEntity findAllList(@PathVariable("uid") Long uid) {
        List<CgFileList> allList = service.findAllList(uid);
        return ResponseEntity.ok(allList);
    }

    /**
     *
     * 파일 썸네일 조회 (단건)
     *
     * @api         [GET] /cguard/api/file/thumbnail/{uid}
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/thumbnail/{uid}")
    public ResponseEntity findThumbnail(@PathVariable("uid") Long uid) {
        CgFileList thumbnail = service.findThumbnail(uid);
        return ResponseEntity.ok(thumbnail);
    }
}
