package hong.CashGuard.global.tus;

/**
 * packageName    : hong.CashGuard.global.tus
 * fileName       : TusFileRestController
 * author         : work
 * date           : 2025-04-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */
public class TusFileRestController {
    /*@RequestMapping("api/tus/files")
    @RestController
    @RequiredArgsConstructor
    @Tag(name = "Tus RestController", description = "첨부파일에 관련된 RestController - tus")
    public class HongTusFileRestController {

        private final HongTusFileService tusFileService;
        private final TusFileUploadService tusFileUploadService;

        @DeleteMapping("temp/delete.json")
        @Operation(summary = "임시파일 삭제", description = "fileUrl을 통해 아직 db에 저장되지 않은 임시파일을 삭제한다.")
        @ApiDocumentResponse
        public void deleteTemp(@RequestParam(name = "fileUrl") String fileUrl) throws TusException, IOException {
            tusFileService.deleteTempUpload(fileUrl);
        }

        @RequestMapping(value = {"upload", "upload/**"}, method = { RequestMethod.POST, RequestMethod.OPTIONS })
        @Operation(summary = "파일 업로드", description = "파일을 업로드 한다.")
        @ApiDocumentResponse
        public void process(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
            tusFileUploadService.process(req, res);
        }

        @DeleteMapping(value = {"upload", "upload/**"})
        @Operation(summary = "파일 삭제", description = "업로드한 파일을 삭제한다.")
        @ApiDocumentResponse
        public void delete(final HttpServletRequest req, final HttpServletResponse res) throws IOException, TusException {
            tusFileUploadService.process(req, res);
            String uploadURI = req.getRequestURI();
            tusFileService.deleteTempUpload(uploadURI);
        }

        @RequestMapping(value={"upload", "upload/**"}, method={ RequestMethod.HEAD, RequestMethod.PATCH })
        @Operation(summary = "파일 업로드", description = "파일을 업로드 한다. (최종 파일 업로드 후, fileId를 헤더에 담아서 리턴한다.)")
        @ApiDocumentResponse
        public ResponseEntity<String> upload(final HttpServletRequest req, final HttpServletResponse res) throws IOException, TusException {
            tusFileUploadService.process(req, res);
            String fileId = tusFileService.sendFileId(req);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("X-File-Id", fileId);
            return ResponseEntity.ok().headers(responseHeaders).build();
        }
    }*/
}
