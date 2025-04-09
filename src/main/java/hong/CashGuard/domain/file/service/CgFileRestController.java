package hong.CashGuard.domain.file.service;

import hong.CashGuard.domain.file.dto.response.CgFileList;
import hong.CashGuard.global.bean.file.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
 * 2025-04-09        work       영수증, 첨부파일 다운로드 API 추가
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

    /**
     *
     * 영수증 이미지 다운로드
     *
     * @api         [GET] /cguard/api/file/receipt/{id}
     * @author      work
     * @date        2025-04-09
    **/
    @GetMapping("/receipt/{id}")
    public ResponseEntity<Resource> downloadReceipt(@PathVariable("id") String id){
        return service.download(id);
    }

    /**
     *
     * 첨부파일 다운로드
     *
     * @api         [GET] /cguard/api/file/download/{id}
     * @author      work
     * @date        2025-04-09
    **/
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return service.download(id);
    }

    /**
     *
     * Tus 업로드 첨부파일 제대로 db 저장되는지 체크용
     *
     * @api         [POST] /cguard/api/file/test
     * @author      work
     * @date        2025-04-09
    **/
    @PostMapping("/test")
    public ResponseEntity test(@RequestBody FileDto request) {
        Long test = service.test(request);
        return ResponseEntity.ok(test);
    }

}
