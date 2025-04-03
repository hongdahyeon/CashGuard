package hong.CashGuard.global.tus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.tus
 * fileName       : TusFileRestController
 * author         : work
 * date           : 2025-04-03
 * description    : Tus 첨부파일과 관련된 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-03        home       로직 구현
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/tus/files")
public class TusFileRestController {

    private final TusFileService fileService;
    private final TusFileUploadService uploadService;

    /**
     *
     * 임시 업로드 파일 삭제
     *
     * @api         [DELETE] /cguard/api/tus/files/temp/delete
     * @author      work
     * @date        2025-04-03
    **/
    @DeleteMapping("/temp/delete")
    public void deleteTemp( @RequestParam("fileUrl") String fileUrl ) throws TusException, IOException {
        fileService.deleteTempUploadFile(fileUrl);
    }

    /**
     *
     * 파일 업로드
     *
     * @api         [POST, OPTIONS] /cguard/api/tus/upload, /cguard/api/tus/upload/**
     * @author      work
     * @date        2025-04-03
    **/
    @RequestMapping(value = {"upload", "upload/**"}, method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public void process(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        uploadService.process(req, res);
    }

    /**
     *
     * 업로드 파일 삭제
     *
     * @api         [DELETE] /cguard/api/tus/upload, /cguard/api/tus/upload/**
     * @author      work
     * @date        2025-04-03
    **/
    @DeleteMapping(value = {"upload", "upload/**"})
    public void delete(final HttpServletRequest req, final HttpServletResponse res) throws TusException, IOException {
        uploadService.process(req, res);;
        String uploadURI = req.getRequestURI();
        fileService.deleteTempUploadFile(uploadURI);
    }

    /**
     *
     * 파일 최종 업로드
     *
     * @api         [HEAD, PATCH] /cguard/api/tus/upload, /cguard/api/tus/upload/**
     * @author      work
     * @date        2025-04-03
    **/
    @RequestMapping(value = {"upload", "upload/**"}, method = {RequestMethod.HEAD, RequestMethod.PATCH})
    public ResponseEntity<String> upload(final HttpServletRequest req, final HttpServletResponse res) throws TusException, IOException {
        uploadService.process(req, res);
        String fileId = fileService.getUploadFileId(req);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("X-File-Id", fileId);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }
}
