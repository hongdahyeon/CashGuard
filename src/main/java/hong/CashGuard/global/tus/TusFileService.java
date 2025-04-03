package hong.CashGuard.global.tus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.tus
 * fileName       : TusFileService
 * author         : work
 * date           : 2025-04-03
 * description    : Tus 첨부파일과 관련된 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-03        home       로직 구현
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TusFileService {

    private final TusFileUploadService uploadService;

    /**
     * @method      getUploadFileId
     * @author      home
     * @date        2025-04-03
     * @deacription {requestURI} 값을 갖고 업로드 파일 정보를 갖고 온다.
    **/
    @Transactional(readOnly = true)
    public String getUploadFileId(final HttpServletRequest req) throws TusException, IOException {
        String uploadURI = req.getRequestURI();
        UploadInfo info = uploadService.getUploadInfo(uploadURI);
        if( info == null ) return null;
        return info.getId().toString();
    }

    /**
     * @method      deleteTempUploadFile
     * @author      home
     * @date        2025-04-03
     * @deacription 임시 저장된 첨부파일 삭제
    **/
    @Transactional
    public void deleteTempUploadFile(String fileURL) throws TusException, IOException {
        uploadService.deleteUpload(fileURL);
    }
}
