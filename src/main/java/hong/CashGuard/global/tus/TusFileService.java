package hong.CashGuard.global.tus;

/**
 * packageName    : hong.CashGuard.global.tus
 * fileName       : TusFileService
 * author         : work
 * date           : 2025-04-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */
public class TusFileService {
    /*@Service
    @RequiredArgsConstructor
    @Slf4j
    public class HongTusFileService {

        private final TusFileUploadService uploadService;

        @Transactional
        public String sendFileId(final HttpServletRequest req) throws TusException, IOException {
            String uploadURI = req.getRequestURI();
            UploadInfo info = uploadService.getUploadInfo(uploadURI);
            if(info == null) return null;
            return info.getId().toString();
        }

        @Transactional
        public void deleteTempUpload(String fileUrl) throws TusException, IOException {
            uploadService.deleteUpload(fileUrl);
        }

    }*/
}
