package hong.CashGuard.domain.file.service;

import hong.CashGuard.domain.code.FileSaved;
import hong.CashGuard.domain.file.domain.CgFile;
import hong.CashGuard.domain.file.domain.CgFileMapper;
import hong.CashGuard.domain.file.dto.response.CgFileList;
import hong.CashGuard.domain.filelog.dto.request.CgFileLogSave;
import hong.CashGuard.domain.filelog.service.CgFileLogService;
import hong.CashGuard.global.bean.file.FileDto;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.FileUtil;
import hong.CashGuard.global.util.StringUtil;
import hong.CashGuard.global.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.file.service
 * fileName       : CgFileService
 * author         : work
 * date           : 2025-04-03
 * description    : 첨부파일 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 * 2025-04-04        work       파일 목록/썸네일 조회
 * 2025-04-09        work       영수증, 첨부파일 다운로드 서비스 로직 추가
 *                              => 영수증 이미지 : root/YYYY/MM 하위로 파일 저장
 */

@Service
@RequiredArgsConstructor
public class CgFileService {

    private final CgFileMapper mapper;
    private final CgFileLogService logService;

    @Value("${hong.receipt.imagePath}")
    private String imagePath;

    @Value("${hong.tus.file.root}")
    private String tusRoot;

    /**
     * @method      test
     * @author      work
     * @date        2025-04-09
     * @deacription Tus 업로드 첨부파일 제대로 db 저장되는지 체크용
    **/
    @Transactional
    public Long test(FileDto fileDto) {
        String filePath = this.getFilePath(fileDto.getFileId());
        Long fileUid = this.generateKey();
        mapper.insert(new CgFile(fileUid, fileDto, filePath));
        return fileUid;
    }

    /**
     * @method      generateKey
     * @author      home
     * @date        2025-04-03
     * @deacription PK 생성
    **/
    private Long generateKey(){
        return mapper.generateKey();
    }
    
    /**
     * @method      getFilePath
     * @author      work
     * @date        2025-04-09
     * @deacription tus 업로드 파일 경로
    **/
    private String getFilePath(String fileId) {
        File targetFile = new File(String.format("%s%s%s%s%s%s%s", tusRoot, File.separator, "uploads", File.separator, fileId, File.separator, "data"));
        return targetFile.getAbsolutePath();
    }

    /**
     * @method      saveAndDelFile
     * @author      home
     * @date        2025-04-03
     * @deacription 첨부파일 저장 및 삭제
     *              * {fileUid} 값은 add, del 값이 있는 경우에만 생성한다.
    **/
    @Transactional
    public Long saveAndDelFile(Long fileUid, FileDto[] addFile, String[] delFile) {

        if( (addFile != null && addFile.length > 0) || (delFile != null && delFile.length > 0) ) {
            if (fileUid == null) {
                fileUid = this.generateKey();
            }
        }

        if( addFile != null && addFile.length > 0 ) {
            for ( FileDto dto : addFile ){
                String filePath = this.getFilePath(dto.getFileId());
                mapper.insert(new CgFile(fileUid, dto, filePath));
            }
        }

        if( delFile != null && delFile.length > 0 ) {
            for ( String fileUrl : delFile ){
                mapper.delete(new CgFile(fileUrl));
            }
        }

        return fileUid;
    }

    /**
     * @method      saveAndDelThumbnail
     * @author      work
     * @date        2025-04-04
     * @deacription 썸네일 첨부파일 저장 및 삭제
     *              * {thumbnail} 값은 add, del 값이 있는 경우에만 생성한다.
    **/
    @Transactional
    public Long saveAndDelThumbnail(Long thumbnail, FileDto addThumbnail, String delThumbnail) {

        if( (addThumbnail != null) || (delThumbnail != null) ) {
            if (thumbnail == null) {
                thumbnail = this.generateKey();
            }
        }

        if( addThumbnail != null ) {
            String filePath = this.getFilePath(addThumbnail.getFileId());
            mapper.insert(new CgFile(thumbnail, addThumbnail, filePath));
        }

        if( delThumbnail != null ) {
            mapper.delete(new CgFile(delThumbnail));
        }

        return thumbnail;
    }


    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-04
     * @deacription 파일 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CgFileList> findAllList(Long uid) {
        return mapper.list(new CgFile(uid, FileSaved.SAVED.name()));
    }

    /**
     * @method      findThumbnail
     * @author      work
     * @date        2025-04-04
     * @deacription 썸네일 이미지 조회
     *              > 썸네일 이미지의 경우 최대 1개만 갖을 수 있다.
    **/
    @Transactional(readOnly = true)
    public CgFileList findThumbnail(Long uid) {
        List<CgFileList> list = mapper.list(new CgFile(uid, FileSaved.SAVED.name()));
        if(list.isEmpty()) return null;

        if(list.size() > 1) {
            throw new CGException("썸네일 이미지는 최대 1개입니다.", HttpStatus.BAD_REQUEST);
        }
        return list.get(0);
    }
    
    
    /**
     * @method      saveReceiptImage
     * @author      work
     * @date        2025-04-08
     * @deacription 업로드한 영수증 이미지 저장
    **/
    @Transactional
    public Long saveReceiptImage(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String uuid = StringUtil.randomUUID();
        String savedFileName = uuid + FileUtil.extension(originalFileName, true);
        String savedFilePath = this.createFolderPath(file, savedFileName);
        String fileUrl = String.format("/cguard/api/file/receipt/%s", uuid);

        FileDto fileBean = FileDto.insertFileDto()
                                  .fileUrl(fileUrl)
                                  .fileId(uuid)
                                  .fileNm(originalFileName)
                                  .fileType(file.getContentType())
                                  .fileSize(Long.toString(file.getSize()))
                                  .extension(FileUtil.extension(originalFileName, false))
                                  .saved(FileSaved.SAVED.name())
                                  .build();
        Long imageUid = this.generateKey();
        mapper.insert(new CgFile(imageUid, fileBean, savedFilePath));
        return imageUid;
    }

    /**
     * @method      createFolderPath
     * @author      work
     * @date        2025-04-09
     * @deacription {YYYY/MM} 형태의 폴더 구조 생성후, {file} 저장
     **/
    private String createFolderPath(MultipartFile file, String fileNm) {
        // 현재 날짜 기반 폴더 경로 생성 (예: 2025/04)
        LocalDate now = TimeUtil.today();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        // 최종 폴더 경로 생성: ${imagePath}/${year}/${month}
        // => Paths.get : OS에 따라 자동으로 경로를 조립한다
        Path folderPath = Paths.get(imagePath, year, month);

        try {

            // 해당 경로가 없다면, 자동으로 상위 디렉토리를 생성한다
            if(!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // 파일의 최종 저장 경로를 완성 (폴더 + 파일 이름)
            // => (ex) ${imagePath}/${year}/${month}/${fileName}
            Path filePath = folderPath.resolve(fileNm);

            // 실제 파일 저장
            file.transferTo(filePath.toFile());

            // 최종 저장된 전체 경로를 문자열로 반환
            return filePath.toString();

        } catch (IOException e) {
            throw new CGException("영수증 이미지 저장 도중에 오류가 발생했습니다. " + e, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * @method      downloadReceipt
     * @author      work
     * @date        2025-04-09
     * @deacription {영수증 이미지} 다운로드
    **/
    @Transactional
    public ResponseEntity<Resource> download(String fileId) {
        CgFile view = this.updateCntAndInsertLog(fileId);
        Resource resource = new FileSystemResource(view.getFilePath());

        String fileName = "";
        try {
            fileName = URLEncoder.encode(view.getFileNm(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        String disposition = "attachment;filename=" + fileName + ";";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", disposition)
                .body(resource);
    }

    private CgFile updateCntAndInsertLog(String fileId) {
        CgFile view = mapper.view(fileId);
        mapper.updateDownloadCnt(view.updateDownloadCnt(fileId));
        logService.saveFileLog(new CgFileLogSave(view.getUid(), view.getFileUrl()));
        return view;
    }
}
